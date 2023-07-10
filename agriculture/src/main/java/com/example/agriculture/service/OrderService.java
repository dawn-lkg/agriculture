package com.example.agriculture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.agriculture.entity.Orders;
import com.example.agriculture.entity.vo.OrderVo;

import java.util.List;

/**
 * (Order)表服务接口
 *
 * @author 陈黎明
 * @since 2023-03-03 18:57:10
 */
public interface OrderService extends IService<Orders> {
    List<OrderVo> userOrder(Integer status, Integer userId);
    List<OrderVo> userOrderAll(Integer userId);
}

