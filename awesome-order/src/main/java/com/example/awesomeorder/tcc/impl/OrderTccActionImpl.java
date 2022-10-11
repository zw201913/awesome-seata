package com.example.awesomeorder.tcc.impl;

import com.example.awesomeorder.dao.entity.OrderTcc;
import com.example.awesomeorder.dao.mapper.OrderTccMapper;
import com.example.awesomeorder.tcc.IOrderTccAction;
import com.example.awesomeorder.tcc.TccActionResultWrap;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author zouwei
 * @className OrderTccActionImpl
 * @date: 2022/10/11 13:05
 * @description:
 */
@Component
public class OrderTccActionImpl implements IOrderTccAction {

	@Resource
	private OrderTccMapper orderTccMapper;


	@Override
	public boolean prepareOrder(BusinessActionContext businessActionContext, String userId, String commodityCode, int count, long unitPrice) {
		String xid = businessActionContext.getXid();
		// 幂等性判断
		if (TccActionResultWrap.hasPrepareResult(xid)) {
			return true;
		}
		// 避免空悬挂，已经执行过回滚了就不能再预留资源
		if (TccActionResultWrap.hasRollbackResult(xid) || TccActionResultWrap.hasCommitResult(xid)) {
			return false;
		}
		// 准备预创建订单
		OrderTcc order = new OrderTcc();
		// 创建时间
		order.setCreateTime(LocalDateTime.now());
		// 用户ID
		order.setUserId(userId);
		// 数量
		order.setCount(count);
		// 商品编码
		order.setCommodityCode(commodityCode);
		// 单价
		order.setUnitPrice(unitPrice);
		// 设置状态
		order.setStatus("预创建");
		// 创建订单
		boolean result = orderTccMapper.insert(order) > 0;
		// 记录主键ID，为了传递给提交或回滚
		businessActionContext.addActionContext("id", order.getId());
		// 记录执行结果：xid:result
		// 以便回滚时判断是否是空回滚
		TccActionResultWrap.prepareSuccess(xid);
		return result;
	}

	@Transactional
	@Override
	public boolean commitOrder(BusinessActionContext businessActionContext) {
		String xid = businessActionContext.getXid();
		// 幂等性判断
		if (TccActionResultWrap.hasCommitResult(xid)) {
			return true;
		}
		// 修改预留资源状态
		Integer id = (Integer) businessActionContext.getActionContext("id");
		OrderTcc row = new OrderTcc();
		row.setId(id);
		row.setStatus("成功");
		boolean result = orderTccMapper.updateByPrimaryKeySelective(row) > 0;
		// 清除预留结果
		TccActionResultWrap.removePrepareResult(xid);
		// 设置提交结果
		TccActionResultWrap.commitSuccess(xid);
		return result;
	}

	@Transactional
	@Override
	public boolean rollbackOrder(BusinessActionContext businessActionContext) {
		String xid = businessActionContext.getXid();
		// 幂等性判断
		if (TccActionResultWrap.hasRollbackResult(xid)) {
			return true;
		}
		// 没有预留资源结果，回滚不做任何处理；
		if (!TccActionResultWrap.hasPrepareResult(xid)) {
			// 设置回滚结果，防止空悬挂
			TccActionResultWrap.rollbackResult(xid);
			return true;
		}
		// 执行回滚，删除预创建订单
		Integer id = (Integer) businessActionContext.getActionContext("id");
		boolean result = orderTccMapper.deleteByPrimaryKey(id) > 0;

		// 清除预留结果
		TccActionResultWrap.removePrepareResult(xid);
		// 设置回滚结果
		TccActionResultWrap.rollbackResult(xid);
		return result;
	}
}
