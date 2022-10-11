package com.example.awesomestorage.dao.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author zouwei
 * @className StockTccEnhanceMapper
 * @date: 2022/10/11 14:07
 * @description:
 */
public interface StockTccEnhanceMapper {

	/**
	 * 预扣减库存
	 *
	 * @param commodityCode
	 * @param count
	 * @return
	 */
	int prepareDeductStock(@Param("commodityCode") String commodityCode, @Param("count") int count);

	/**
	 * 提交预扣减库存
	 *
	 * @param commodityCode
	 * @param count
	 * @return
	 */
	int commitDeductStock(@Param("commodityCode") String commodityCode, @Param("count") int count);

	/**
	 * 回滚预扣减库存
	 *
	 * @param commodityCode
	 * @param count
	 * @return
	 */
	int rollbackDeductStock(@Param("commodityCode") String commodityCode, @Param("count") int count);
}
