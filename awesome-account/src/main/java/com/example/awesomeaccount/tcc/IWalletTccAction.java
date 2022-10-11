package com.example.awesomeaccount.tcc;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author zouwei
 * @className IWalletTccAction
 * @date: 2022/10/10 20:50
 * @description:
 */
@LocalTCC
public interface IWalletTccAction {

	/**
	 * 预扣款
	 *
	 * @param businessActionContext
	 * @param userId
	 * @param amount
	 * @return
	 */
	@TwoPhaseBusinessAction(name = "prepareDeductMoney", commitMethod = "commitDeductMoney", rollbackMethod = "rollbackDeductMoney")
	boolean prepareDeductMoney(BusinessActionContext businessActionContext,
	                           @BusinessActionContextParameter(paramName = "userId") String userId,
	                           @BusinessActionContextParameter(paramName = "amount") Long amount);

	/**
	 * 提交扣款
	 *
	 * @param businessActionContext
	 * @return
	 */
	boolean commitDeductMoney(BusinessActionContext businessActionContext);

	/**
	 * 回滚扣款
	 *
	 * @param businessActionContext
	 * @return
	 */
	boolean rollbackDeductMoney(BusinessActionContext businessActionContext);
}
