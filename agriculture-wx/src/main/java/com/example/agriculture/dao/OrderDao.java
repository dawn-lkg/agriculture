package com.example.agriculture.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.agriculture.entity.Orders;
import com.example.agriculture.entity.vo.OrderVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (Order)表数据库访问层
 *
 * @author 陈黎明
 * @since 2023-03-03 18:55:32
 */
@Mapper
public interface OrderDao extends BaseMapper<Orders> {
    List<OrderVo> userOrder(Integer status, Integer userId);
    List<OrderVo> userOrderAll(Integer userId);
}

