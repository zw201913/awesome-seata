package com.example.orderapi.api;

import com.example.orderapi.model.OrderInfo;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zouwei
 * @className OrderApi
 * @date: 2022/9/27 22:57
 * @description:
 */
public interface OrderApi {

	@PutMapping("/create4At")
	Boolean createOrder4AT(@RequestBody OrderInfo orderInfo);

	@PutMapping("/create4XA")
	Boolean createOrder4XA(@RequestBody OrderInfo orderInfo);

	@PutMapping("/create4TCC")
	Boolean createOrder4TCC(@RequestBody OrderInfo orderInfo);
}
