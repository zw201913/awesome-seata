package com.example.awesomeorder.controller;

import com.example.awesomeorder.service.IOrderService;
import com.example.orderapi.api.OrderApi;
import com.example.orderapi.model.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zouwei
 * @className OrderController
 * @date: 2022/9/27 22:55
 * @description:
 */
@RestController
@RequestMapping("/order")
public class OrderController implements OrderApi {

	@Autowired
	private IOrderService orderService;

	@Override
	public Boolean createOrder(OrderInfo orderInfo) {
		String commodityCode = orderInfo.getCommodityCode();
		int count = orderInfo.getCount();
		long unitPrice = orderInfo.getUnitPrice();
		String userId = orderInfo.getUserId();
		return orderService.createOrder(userId, commodityCode, count, unitPrice);
	}
}
