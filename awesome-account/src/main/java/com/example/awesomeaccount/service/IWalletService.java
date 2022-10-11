package com.example.awesomeaccount.service;

/**
 * @author zouwei
 * @className WalletService
 * @date: 2022/9/27 21:20
 * @description:
 */
public interface IWalletService {
	/**
	 * 扣钱
	 *
	 * @param userId
	 * @param amount
	 * @return
	 */
	Boolean deductMoney4AT(String userId, long amount);

	/**
	 * 预扣款
	 *
	 * @param userId
	 * @param amount
	 * @return
	 */
	Boolean deductMoney4TCC(String userId, long amount);
}
