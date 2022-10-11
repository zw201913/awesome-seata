package com.example.awesomeorder.dao.mapper;

import com.example.awesomeorder.dao.entity.OrderTcc;
import com.example.awesomeorder.dao.entity.OrderTccExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderTccMapper {
    long countByExample(OrderTccExample example);

    int deleteByExample(OrderTccExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderTcc row);

    int insertSelective(OrderTcc row);

    List<OrderTcc> selectByExample(OrderTccExample example);

    OrderTcc selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") OrderTcc row, @Param("example") OrderTccExample example);

    int updateByExample(@Param("row") OrderTcc row, @Param("example") OrderTccExample example);

    int updateByPrimaryKeySelective(OrderTcc row);

    int updateByPrimaryKey(OrderTcc row);
}