package com.example.awesomeorder.service.impl;

import com.example.awesomeorder.api.StockApiClient;
import com.example.awesomeorder.dao.entity.Order;
import com.example.awesomeorder.dao.mapper.OrderMapper;
import com.example.awesomeorder.service.IOrderService;
import com.example.storageapi.model.OrderInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author zouwei
 * @className OrderServiceImpl
 * @date: 2022/9/27 22:47
 * @description:
 */
@Service
public class OrderServiceImpl implements IOrderService {

	@Resource
	private OrderMapper orderMapper;

	@Resource
	private StockApiClient stockApiClient;

	/**
	 * 创建订单
	 *
	 * @param userId
	 * @param commodityCode
	 * @param count
	 * @param unitPrice
	 * @return
	 */
	@Transactional
	@Override
	public Boolean createOrder(String userId, String commodityCode, int count, long unitPrice) {
		// 构建待扣减的库存信息
		OrderInfo orderInfo = new OrderInfo();
		// 设置商品编码
		orderInfo.setCommodityCode(commodityCode);
		// 设置需要扣减的数量
		orderInfo.setCount(count);
		// 先构建库存
		if (stockApiClient.deductStock(orderInfo)) {
			// 扣减库存成功后，准备创建订单
			Order order = new Order();
			// 创建时间
			order.setCreateTime(LocalDateTime.now());
			// 用户ID
			order.setUserId(userId);
			// 数量
			order.setCount(count);
			// 商品编码
			order.setCommodityCode(commodityCode);
			// 单价
			order.setUnitPrice(unitPrice);
			// 创建订单
			return orderMapper.insert(order) > 0;
		}
		// 扣减库存失败，订单创建失败
		return Boolean.FALSE;
	}
}
