package com.example.awesomestorage.dao.mapper;

import com.example.awesomestorage.dao.entity.StockXA;
import com.example.awesomestorage.dao.entity.StockXAExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockXAMapper {
    long countByExample(StockXAExample example);

    int deleteByExample(StockXAExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StockXA row);

    int insertSelective(StockXA row);

    List<StockXA> selectByExample(StockXAExample example);

    StockXA selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") StockXA row, @Param("example") StockXAExample example);

    int updateByExample(@Param("row") StockXA row, @Param("example") StockXAExample example);

    int updateByPrimaryKeySelective(StockXA row);

    int updateByPrimaryKey(StockXA row);
}