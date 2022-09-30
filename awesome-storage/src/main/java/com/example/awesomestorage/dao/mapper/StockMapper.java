package com.example.awesomestorage.dao.mapper;

import com.example.awesomestorage.dao.entity.Stock;
import com.example.awesomestorage.dao.entity.StockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockMapper {
    long countByExample(StockExample example);

    int deleteByExample(StockExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Stock row);

    int insertSelective(Stock row);

    List<Stock> selectByExample(StockExample example);

    Stock selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") Stock row, @Param("example") StockExample example);

    int updateByExample(@Param("row") Stock row, @Param("example") StockExample example);

    int updateByPrimaryKeySelective(Stock row);

    int updateByPrimaryKey(Stock row);
}