package com.example.awesomebusiness.service.impl;

import com.example.accountapi.model.AmountInfo;
import com.example.awesomebusiness.api.OrderApiClient;
import com.example.awesomebusiness.api.WalletApiClient;
import com.example.awesomebusiness.service.ShoppingCartService;
import com.example.orderapi.model.OrderInfo;
import io.seata.core.exception.GlobalTransactionException;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zouwei
 * @className ShoppingCartServiceImpl
 * @date: 2022/9/18 14:01
 * @description:
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	// 钱包服务
	@Resource
	private WalletApiClient walletApiClient;
	// 订单服务
	@Resource
	private OrderApiClient orderApiClient;

	/**
	 * 购物车下单（AT模式）
	 *
	 * @return
	 * @throws GlobalTransactionException
	 */
	// 别忘记了这个注解，这是开启分布式事务的标记，前提是当前业务逻辑不处于任何的分布式事务当中才能开启新的分布式事务
	@GlobalTransactional
	public String placeOrder4AT() throws GlobalTransactionException {
		// 模拟用户ID 123456，对应数据库初始化的用户ID
		String userId = "123456";
		// 构建订单数据
		OrderInfo orderInfo = new OrderInfo();
		// 数量15个
		orderInfo.setCount(15);
		// 商品编码，对应库存数据表的初始化数据
		orderInfo.setCommodityCode("CC-54321");
		// 单价299，默认是long类型，单位分；避免double精度丢失
		orderInfo.setUnitPrice(299);
		// 订单归属
		orderInfo.setUserId(userId);
		// 计算扣款金额，数量*单价
		long amount = orderInfo.getCount() * orderInfo.getUnitPrice();
		// 构建扣款数据
		AmountInfo amountInfo = new AmountInfo();
		// 设置扣款金额
		amountInfo.setAmount(amount);
		// 设置扣款主体
		amountInfo.setUserId(userId);
		// 先扣款，扣款成功就创建订单，扣减库存在创建订单的逻辑里面
		if (walletApiClient.deductMoney4AT(amountInfo) && orderApiClient.createOrder4AT(orderInfo)) {
			return "下单成功！";
		}
		// 1.扣款失败，抛异常，分布式事务回滚
		// 2.创建订单失败，抛异常，分布式事务回滚
		throw new GlobalTransactionException("下单失败！");
	}

	@Override
	public String placeOrder4XA() throws GlobalTransactionException {
		// 模拟用户ID 123456，对应数据库初始化的用户ID
		String userId = "123456";
		// 构建订单数据
		OrderInfo orderInfo = new OrderInfo();
		// 数量15个
		orderInfo.setCount(15);
		// 商品编码，对应库存数据表的初始化数据
		orderInfo.setCommodityCode("CC-54321");
		// 单价299，默认是long类型，单位分；避免double精度丢失
		orderInfo.setUnitPrice(299);
		// 订单归属
		orderInfo.setUserId(userId);
		// 计算扣款金额，数量*单价
		long amount = orderInfo.getCount() * orderInfo.getUnitPrice();
		// 构建扣款数据
		AmountInfo amountInfo = new AmountInfo();
		// 设置扣款金额
		amountInfo.setAmount(amount);
		// 设置扣款主体
		amountInfo.setUserId(userId);
		// 先扣款，扣款成功就创建订单，扣减库存在创建订单的逻辑里面
		if (walletApiClient.deductMoney4XA(amountInfo) && orderApiClient.createOrder4XA(orderInfo)) {
			return "下单成功！";
		}
		// 1.扣款失败，抛异常，分布式事务回滚
		// 2.创建订单失败，抛异常，分布式事务回滚
		throw new GlobalTransactionException("下单失败！");
	}


	/**
	 * 购物车下单（TCC模式）
	 *
	 * @return
	 * @throws GlobalTransactionException
	 */
	@GlobalTransactional
	public String placeOrder4TCC() throws GlobalTransactionException {
		// 模拟用户ID 123456，对应数据库初始化的用户ID
		String userId = "123456";
		// 构建订单数据
		OrderInfo orderInfo = new OrderInfo();
		// 数量15个
		orderInfo.setCount(15);
		// 商品编码，对应库存数据表的初始化数据
		orderInfo.setCommodityCode("CC-54321");
		// 单价299，默认是long类型，单位分；避免double精度丢失
		orderInfo.setUnitPrice(299);
		// 订单归属
		orderInfo.setUserId(userId);
		// 计算扣款金额，数量*单价
		long amount = orderInfo.getCount() * orderInfo.getUnitPrice();
		// 构建扣款数据
		AmountInfo amountInfo = new AmountInfo();
		// 设置扣款金额
		amountInfo.setAmount(amount);
		// 设置扣款主体
		amountInfo.setUserId(userId);
		// 先扣款，扣款成功就创建订单，扣减库存在创建订单的逻辑里面
		if (walletApiClient.deductMoney4TCC(amountInfo) && orderApiClient.createOrder4TCC(orderInfo)) {
			return "下单成功！";
		}
		// 1.扣款失败，抛异常，分布式事务回滚
		// 2.创建订单失败，抛异常，分布式事务回滚
		throw new GlobalTransactionException("下单失败！");
	}
}