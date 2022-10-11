package com.example.awesomestorage.service.impl;

import com.example.awesomestorage.dao.mapper.StockEnhanceMapper;
import com.example.awesomestorage.service.IStockService;
import com.example.awesomestorage.tcc.IStorageTccAction;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private IStorageTccAction storageTccAction;

	/**
	 * 扣减库存（AT模式）
	 *
	 * @param commodityCode
	 * @param count
	 * @return
	 */
	@Transactional
	@Override
	public Boolean deductStock4AT(String commodityCode, int count) {
		// 扣减库存，判断影响行数是否大于0
		// sql如下： update stock_tbl set count = count - #{count,jdbcType=INTEGER} where commodity_code = #{commodityCode,jdbcType=VARCHAR} and count <![CDATA[ >= ]]> #{count,jdbcType=INTEGER}
		return stockEnhanceMapper.deductStock(commodityCode, count) > 0;
	}

	/**
	 * 预扣库存（TCC模式）
	 *
	 * @param commodityCode
	 * @param count
	 * @return
	 */
	@Transactional
	@Override
	public Boolean deductStock4TCC(String commodityCode, int count) {
		return storageTccAction.prepareDeductStock(null, commodityCode, count);
	}
}
