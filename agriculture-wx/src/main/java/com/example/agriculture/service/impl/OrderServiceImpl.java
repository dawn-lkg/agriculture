package com.example.agriculture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.agriculture.dao.OrderDao;
import com.example.agriculture.entity.Orders;
import com.example.agriculture.entity.vo.OrderVo;
import com.example.agriculture.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Order)表服务实现类
 *
 * @author 陈黎明
 * @since 2023-03-03 18:57:11
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, Orders> implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Override
    public List<OrderVo> userOrder(Integer status, Integer userId) {
        return orderDao.userOrder(status,userId);
    }

    @Override
    public List<OrderVo> userOrderAll(Integer userId) {
        return orderDao.userOrderAll(userId);
    }
}

