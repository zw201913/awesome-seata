package com.example.awesomeaccount.dao.mapper;

import com.example.awesomeaccount.dao.entity.Wallet;
import com.example.awesomeaccount.dao.entity.WalletExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletMapper {
    long countByExample(WalletExample example);

    int deleteByExample(WalletExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Wallet row);

    int insertSelective(Wallet row);

    List<Wallet> selectByExample(WalletExample example);

    Wallet selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") Wallet row, @Param("example") WalletExample example);

    int updateByExample(@Param("row") Wallet row, @Param("example") WalletExample example);

    int updateByPrimaryKeySelective(Wallet row);

    int updateByPrimaryKey(Wallet row);
}