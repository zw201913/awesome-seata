package com.example.awesomeaccount.dao.mapper;

import com.example.awesomeaccount.dao.entity.WalletXA;
import com.example.awesomeaccount.dao.entity.WalletXAExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletXAMapper {
    long countByExample(WalletXAExample example);

    int deleteByExample(WalletXAExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WalletXA row);

    int insertSelective(WalletXA row);

    List<WalletXA> selectByExample(WalletXAExample example);

    WalletXA selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") WalletXA row, @Param("example") WalletXAExample example);

    int updateByExample(@Param("row") WalletXA row, @Param("example") WalletXAExample example);

    int updateByPrimaryKeySelective(WalletXA row);

    int updateByPrimaryKey(WalletXA row);
}