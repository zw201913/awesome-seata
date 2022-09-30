package com.example.storageapi.api;

import com.example.storageapi.model.OrderInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zouwei
 * @className StockApi
 * @date: 2022/9/28 00:26
 * @description:
 */
public interface StockApi {

	@PostMapping("/deduct")
	Boolean deductStock(@RequestBody OrderInfo orderInfo);
}
