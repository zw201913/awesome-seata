package com.example.awesomeaccount.service.impl;

import com.example.awesomeaccount.dao.mapper.WalletEnhanceMapper;
import com.example.awesomeaccount.service.IWalletService;
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

	@Transactional
	@Override
	public Boolean deductMoney(String userId, long amount) {
		return walletEnhanceMapper.deductMoney(userId, amount) > 0;
	}
}
