package com.example.awesomeaccount.controller;

import com.example.accountapi.api.WalletApi;
import com.example.accountapi.model.AmountInfo;
import com.example.awesomeaccount.service.IWalletService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WalletApi接口在account-api模块中
 *
 * @author zouwei
 * @className WalletController
 * @date: 2022/9/18 14:58
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/wallet")
public class WalletController implements WalletApi {

	@Autowired
	private IWalletService walletService;

	@Override
	public Boolean deductMoney4AT(AmountInfo amountInfo) {
		String userId = amountInfo.getUserId();
		long amount = amountInfo.getAmount();
		// 打印分布式事务XID
		log.warn("XID:{}", RootContext.getXID());
		// 扣款
		return walletService.deductMoney4AT(userId, amount);
	}

	@Override
	public Boolean deductMoney4TCC(AmountInfo amountInfo) {
		String userId = amountInfo.getUserId();
		long amount = amountInfo.getAmount();
		// 打印分布式事务XID
		log.warn("XID:{}", RootContext.getXID());
		// 扣款
		return walletService.deductMoney4TCC(userId, amount);
	}
}
