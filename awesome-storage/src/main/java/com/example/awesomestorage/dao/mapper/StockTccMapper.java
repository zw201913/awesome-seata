package com.example.awesomestorage.dao.mapper;

import com.example.awesomestorage.dao.entity.StockTcc;
import com.example.awesomestorage.dao.entity.StockTccExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockTccMapper {
    long countByExample(StockTccExample example);

    int deleteByExample(StockTccExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StockTcc row);

    int insertSelective(StockTcc row);

    List<StockTcc> selectByExample(StockTccExample example);

    StockTcc selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") StockTcc row, @Param("example") StockTccExample example);

    int updateByExample(@Param("row") StockTcc row, @Param("example") StockTccExample example);

    int updateByPrimaryKeySelective(StockTcc row);

    int updateByPrimaryKey(StockTcc row);
}