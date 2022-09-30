package com.example.accountapi.api;

import com.example.accountapi.model.AmountInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 供生产者和消费者共同使用
 *
 * @author zouwei
 * @className AccountApi
 * @date: 2022/9/18 14:31
 * @description:
 */
public interface WalletApi {

	/**
	 * 扣钱
	 *
	 * @param amountInfo
	 * @return
	 */
	@PostMapping("/deductMoney")
	Boolean deductMoney(@RequestBody AmountInfo amountInfo);
}
