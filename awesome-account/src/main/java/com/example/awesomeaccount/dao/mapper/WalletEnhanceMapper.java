package com.example.awesomeaccount.dao.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author zouwei
 * @className WalletEnhanceMapper
 * @date: 2022/9/27 21:33
 * @description:
 */
public interface WalletEnhanceMapper {

	int deductMoney(@Param("userId") String userId, @Param("amount") long amount);
}
