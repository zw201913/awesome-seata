package com.example.awesomestorage.tcc;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author zouwei
 * @className IStorageTccAction
 * @date: 2022/10/11 13:57
 * @description:
 */
@LocalTCC
public interface IStorageTccAction {
	/**
	 * 预扣库存
	 *
	 * @param businessActionContext
	 * @param commodityCode
	 * @param count
	 * @return
	 */
	@TwoPhaseBusinessAction(name = "prepareDeductStock", commitMethod = "commitDeductStock", rollbackMethod = "rollbackDeductStock")
	boolean prepareDeductStock(BusinessActionContext businessActionContext,
	                           @BusinessActionContextParameter("commodityCode") String commodityCode,
	                           @BusinessActionContextParameter("count") int count);

	/**
	 * 提交被扣库存
	 *
	 * @param businessActionContext
	 * @return
	 */
	boolean commitDeductStock(BusinessActionContext businessActionContext);

	/**
	 * 回滚被扣库存
	 *
	 * @param businessActionContext
	 * @return
	 */
	boolean rollbackDeductStock(BusinessActionContext businessActionContext);
}
