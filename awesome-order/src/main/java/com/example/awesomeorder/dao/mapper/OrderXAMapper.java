package com.example.awesomeorder.dao.mapper;

import com.example.awesomeorder.dao.entity.OrderXA;
import com.example.awesomeorder.dao.entity.OrderXAExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderXAMapper {
    long countByExample(OrderXAExample example);

    int deleteByExample(OrderXAExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderXA row);

    int insertSelective(OrderXA row);

    List<OrderXA> selectByExample(OrderXAExample example);

    OrderXA selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") OrderXA row, @Param("example") OrderXAExample example);

    int updateByExample(@Param("row") OrderXA row, @Param("example") OrderXAExample example);

    int updateByPrimaryKeySelective(OrderXA row);

    int updateByPrimaryKey(OrderXA row);
}