package com.example.awesomeaccount.dao.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author zouwei
 * @className WalletTccEnhanceMapper
 * @date: 2022/10/11 12:28
 * @description:
 */
public interface WalletTccEnhanceMapper {

	int prepareDeductMoney(@Param("userId") String userId, @Param("amount") long amount);

	int commitDeductMoney(@Param("userId") String userId, @Param("amount") long amount);

	int rollbackDeductMoney(@Param("userId") String userId, @Param("amount") long amount);
}
