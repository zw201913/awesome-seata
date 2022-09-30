package com.example.awesomestorage.dao.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author zouwei
 * @className StockEnhanceMapper
 * @date: 2022/9/28 00:16
 * @description:
 */
public interface StockEnhanceMapper {
	/**
	 * 扣减库存
	 *
	 * @param commodityCode
	 * @param count
	 * @return
	 */
	int deductStock(@Param("commodityCode") String commodityCode, @Param("count") int count);
}
