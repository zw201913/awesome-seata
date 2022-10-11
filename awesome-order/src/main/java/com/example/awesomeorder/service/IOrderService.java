package com.example.awesomeorder.service;

/**
 * @author zouwei
 * @className OrderService
 * @date: 2022/9/27 22:47
 * @description:
 */
public interface IOrderService {

	Boolean createOrder4AT(String userId, String commodityCode, int count, long unitPrice);

	Boolean createOrder4TCC(String userId, String commodityCode, int count, long unitPrice);
}
