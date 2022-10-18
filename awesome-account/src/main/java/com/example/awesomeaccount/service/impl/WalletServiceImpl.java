package com.example.awesomeaccount.service.impl;

import com.example.awesomeaccount.dao.mapper.WalletEnhanceMapper;
import com.example.awesomeaccount.dao.mapper.WalletXAEnhanceMapper;
import com.example.awesomeaccount.service.IWalletService;
import com.example.awesomeaccount.tcc.IWalletTccAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zouwei
 * @className WalletServiceImpl
 * @date: 2022/9/27 21:20
 * @description:
 */
@Service
public class WalletServiceImpl implements IWalletService {

	@Resource
	private WalletEnhanceMapper walletEnhanceMapper;

	@Resource
	private WalletXAEnhanceMapper walletXAEnhanceMapper;

	@Autowired
	private IWalletTccAction walletTccAction;

	/**
	 * 扣款（AT模式）
	 *
	 * @param userId
	 * @param amount
	 * @return
	 */
	@Transactional
	@Override
	public Boolean deductMoney4AT(String userId, long amount) {
		return walletEnhanceMapper.deductMoney(userId, amount) > 0;
	}

	/**
	 * 扣款（XA模式）
	 *
	 * @param userId
	 * @param amount
	 * @return
	 */
	@Transactional
	@Override
	public Boolean deductMoney4XA(String userId, long amount) {
		return walletXAEnhanceMapper.deductMoney(userId, amount) > 0;
	}

	/**
	 * 预扣款（TCC模式）
	 *
	 * @param userId
	 * @param amount
	 * @return
	 */
	@Transactional
	@Override
	public Boolean deductMoney4TCC(String userId, long amount) {
		return walletTccAction.prepareDeductMoney(null, userId, amount);
	}
}
