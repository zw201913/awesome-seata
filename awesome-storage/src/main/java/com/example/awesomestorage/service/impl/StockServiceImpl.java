package com.example.awesomestorage.service.impl;

import com.example.awesomestorage.dao.mapper.StockEnhanceMapper;
import com.example.awesomestorage.service.IStockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zouwei
 * @className StockServiceImpl
 * @date: 2022/9/28 00:06
 * @description:
 */
@Service
public class StockServiceImpl implements IStockService {

	@Resource
	private StockEnhanceMapper stockEnhanceMapper;

	@Transactional
	@Override
	public Boolean deductStock(String commodityCode, int count) {
		// 扣减库存，判断影响行数是否大于0
		// sql如下： update stock_tbl set count = count - #{count,jdbcType=INTEGER} where commodity_code = #{commodityCode,jdbcType=VARCHAR} and count <![CDATA[ >= ]]> #{count,jdbcType=INTEGER}
		return stockEnhanceMapper.deductStock(commodityCode, count) > 0;
	}
}
