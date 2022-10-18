package com.example.awesomebusiness.service;

import io.seata.core.exception.GlobalTransactionException;

/**
 * @author zouwei
 * @className ShoppingCartService
 * @date: 2022/9/18 14:01
 * @description:
 */
public interface ShoppingCartService {
	// 下单
	String placeOrder4AT() throws GlobalTransactionException;

	// 下单
	String placeOrder4XA() throws GlobalTransactionException;

	// 下单
	String placeOrder4TCC() throws GlobalTransactionException;
}
