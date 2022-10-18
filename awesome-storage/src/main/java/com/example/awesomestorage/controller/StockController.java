package com.example.awesomestorage.controller;

import com.example.awesomestorage.service.IStockService;
import com.example.storageapi.api.StockApi;
import com.example.storageapi.model.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zouwei
 * @className StockController
 * @date: 2022/9/28 00:23
 * @description:
 */
@RestController
@RequestMapping("/stock")
public class StockController implements StockApi {

	@Autowired
	private IStockService stockService;

	@Override
	public Boolean deductStock4AT(OrderInfo orderInfo) {
		String commodityCode = orderInfo.getCommodityCode();
		int count = orderInfo.getCount();
		return stockService.deductStock4AT(commodityCode, count);
	}

	@Override
	public Boolean deductStock4XA(OrderInfo orderInfo) {
		String commodityCode = orderInfo.getCommodityCode();
		int count = orderInfo.getCount();
		return stockService.deductStock4XA(commodityCode, count);
	}

	@Override
	public Boolean deductStock4TCC(OrderInfo orderInfo) {
		String commodityCode = orderInfo.getCommodityCode();
		int count = orderInfo.getCount();
		return stockService.deductStock4TCC(commodityCode, count);
	}
}
