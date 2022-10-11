package com.example.awesomeorder.tcc;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author zouwei
 * @className IOrderTccAction
 * @date: 2022/10/11 12:58
 * @description:
 */
@LocalTCC
public interface IOrderTccAction {
	/**
	 * 预创建订单
	 *
	 * @param businessActionContext
	 * @param userId
	 * @param commodityCode
	 * @param count
	 * @param unitPrice
	 * @return
	 */
	@TwoPhaseBusinessAction(name = "prepareOrder", commitMethod = "commitOrder", rollbackMethod = "rollbackOrder")
	boolean prepareOrder(BusinessActionContext businessActionContext,
	                     @BusinessActionContextParameter(paramName = "userId") String userId,
	                     @BusinessActionContextParameter(paramName = "userId") String commodityCode,
	                     @BusinessActionContextParameter(paramName = "userId") int count,
	                     @BusinessActionContextParameter(paramName = "userId") long unitPrice);

	/**
	 * 订单生效
	 *
	 * @param businessActionContext
	 * @return
	 */
	boolean commitOrder(BusinessActionContext businessActionContext);

	/**
	 * 回滚预创建订单
	 *
	 * @param businessActionContext
	 * @return
	 */
	boolean rollbackOrder(BusinessActionContext businessActionContext);
}
