package com.example.awesomeaccount.dao.mapper;

import com.example.awesomeaccount.dao.entity.WalletTcc;
import com.example.awesomeaccount.dao.entity.WalletTccExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletTccMapper {
    long countByExample(WalletTccExample example);

    int deleteByExample(WalletTccExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WalletTcc row);

    int insertSelective(WalletTcc row);

    List<WalletTcc> selectByExample(WalletTccExample example);

    WalletTcc selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") WalletTcc row, @Param("example") WalletTccExample example);

    int updateByExample(@Param("row") WalletTcc row, @Param("example") WalletTccExample example);

    int updateByPrimaryKeySelective(WalletTcc row);

    int updateByPrimaryKey(WalletTcc row);
}