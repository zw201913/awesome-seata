package com.example.awesomeaccount.tcc.impl;

import com.example.awesomeaccount.dao.mapper.WalletTccEnhanceMapper;
import com.example.awesomeaccount.tcc.IWalletTccAction;
import com.example.awesomeaccount.tcc.TccActionResultWrap;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zouwei
 * @className WalletTccActionImpl
 * @date: 2022/10/10 21:05
 * @description:
 */
@Component
public class WalletTccActionImpl implements IWalletTccAction {

	@Resource
	private WalletTccEnhanceMapper walletTccEnhanceMapper;

	@Override
	public boolean prepareDeductMoney(BusinessActionContext businessActionContext, String userId, Long amount) {
		String xid = businessActionContext.getXid();
		// 幂等性判断
		if (TccActionResultWrap.hasPrepareResult(xid)) {
			return true;
		}
		// 避免空悬挂，已经执行过回滚了就不能再预留资源
		if (TccActionResultWrap.hasRollbackResult(xid) || TccActionResultWrap.hasCommitResult(xid)) {
			return false;
		}
		// 预留资源
		boolean result = walletTccEnhanceMapper.prepareDeductMoney(userId, amount) > 0;
		// 记录执行结果：xid:result
		// 以便回滚时判断是否是空回滚
		TccActionResultWrap.prepareSuccess(xid);
		return result;
	}

	@Transactional
	@Override
	public boolean commitDeductMoney(BusinessActionContext businessActionContext) {
		String xid = businessActionContext.getXid();
		// 幂等性判断
		if (TccActionResultWrap.hasCommitResult(xid)) {
			return true;
		}
		Map<String, Object> actionContext = businessActionContext.getActionContext();
		String userId = (String) actionContext.get("userId");
		long amount = (Integer) actionContext.get("amount");
		// 执行提交操作，扣除预留款
		boolean result = walletTccEnhanceMapper.commitDeductMoney(userId, amount) > 0;
		// 清除预留结果
		TccActionResultWrap.removePrepareResult(xid);
		// 设置提交结果
		TccActionResultWrap.commitSuccess(xid);
		return result;
	}

	@Transactional
	@Override
	public boolean rollbackDeductMoney(BusinessActionContext businessActionContext) {
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
		// 执行回滚
		Map<String, Object> actionContext = businessActionContext.getActionContext();
		String userId = (String) actionContext.get("userId");
		long amount = (Integer) actionContext.get("amount");
		boolean result = walletTccEnhanceMapper.rollbackDeductMoney(userId, amount) > 0;
		// 清除预留结果
		TccActionResultWrap.removePrepareResult(xid);
		// 设置回滚结果
		TccActionResultWrap.rollbackResult(xid);
		return result;
	}
}
