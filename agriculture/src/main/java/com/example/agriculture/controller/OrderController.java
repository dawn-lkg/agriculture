package com.example.agriculture.controller;

import cn.hutool.db.sql.Order;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.agriculture.constants.SystemConstants;
import com.example.agriculture.entity.*;
import com.example.agriculture.service.GoodService;
import com.example.agriculture.service.OrderService;

import com.example.agriculture.service.UserService;
import com.example.agriculture.utils.JwtTokenUtil;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (Order)表控制层
 *
 * @author 陈黎明
 * @since 2023-02-25 13:48:26
 */
@RestController
@RequestMapping("order")
public class OrderController {
    /**
     * 服务对象
     */
    @Resource
    private OrderService orderService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private UserService userService;
    @GetMapping("/my")
    public Result userOrder(Integer status,HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        Integer id= Integer.valueOf(JwtTokenUtil.parseToken(token));
        System.out.println(status);
        if(status==null){
            return Result.ok(orderService.userOrderAll(id));
        }
        return Result.ok(orderService.userOrder(status,id));
    }
//    @GetMapping("/add")
//    @Transactional
//    public Result add(@RequestBody Orders order, HttpServletRequest httpServletRequest){
//        String token = httpServletRequest.getHeader("token");
//        Integer id= Integer.valueOf(JwtTokenUtil.parseToken(token));
//        Integer goodId = order.getGoodId();
//        Good byId = goodService.getById(goodId);
//        Double price = byId.getPrice();
//        Double v=order.getQuantity()*price;
//        User user = userService.getById(id);
//        Double balance = user.getBalance();
//        if(balance<v){
//            return Result.error(AppHttpCodeEnum.BALANCE_DUE);
//        }
//        user.setBalance(balance-v);
//        userService.updateById(user);
//        order.setDate(new Date());
//        order.setUserId(id);
//        boolean save = orderService.save(order);
//        return Result.ok(save,"购买成功",200);
//    }
    @PostMapping("add")
    @Transactional
    public Result add(@RequestBody Orders order,HttpServletRequest httpServletRequest){
        Integer id=Integer.valueOf(JwtTokenUtil.parseToken(httpServletRequest.getHeader("token")));
        order.setDate(new Date());
        order.setUserId(id);
        order.setStatus(SystemConstants.UNPAID);
        boolean save = orderService.save(order);
        if(!save) return Result.error(AppHttpCodeEnum.SYSTEM_ERROR);
        return Result.ok(order.getId());
    }
    @DeleteMapping("{id}")
    public Result delete(@PathVariable Integer id){
        boolean removeById = orderService.removeById(id);
        return Result.ok(removeById);
    }
    @PostMapping("/paid")
    @Transactional
    public Result charge(@RequestBody Orders order,HttpServletRequest httpServletRequest){
        Integer id = order.getId();
        Integer userId=Integer.valueOf(JwtTokenUtil.parseToken(httpServletRequest.getHeader("token")));
        User byId = userService.getById(userId);
        Double balance = byId.getBalance();
        LambdaQueryWrapper<Orders> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getId,id);
        Orders one = orderService.getOne(lambdaQueryWrapper);
        Integer goodId = one.getGoodId();
        Good good = goodService.getById(goodId);
        Double price = good.getPrice();
        Integer num = one.getQuantity();
        if(balance<num*price){
            return Result.error(AppHttpCodeEnum.BALANCE_DUE);
        }
        byId.setBalance(balance-(num*price));
        userService.updateById(byId);
        one.setStatus(SystemConstants.PAID);
        boolean b = orderService.updateById(one);
        return Result.ok(b);
    }
    @PutMapping("/receipt")
    @Transactional
    public Result  receipt(@RequestBody Orders order){
        Orders byId = orderService.getById(order.getId());
        byId.setStatus(SystemConstants.EVALUATE);
        boolean b = orderService.updateById(byId);
        return Result.ok(b);
    }
    @PostMapping("addList")
    @Transactional
    public Result addList(@RequestBody OrderList orderList,HttpServletRequest httpServletRequest){
        Integer userId=Integer.valueOf(JwtTokenUtil.parseToken(httpServletRequest.getHeader("token")));
        User byId = userService.getById(userId);
        if(byId.getBalance()<orderList.getTotal()){
            return Result.ok(false,AppHttpCodeEnum.BALANCE_DUE);
        }
        byId.setBalance(byId.getBalance()-orderList.getTotal());
        List<Orders> list = orderList.getList().stream().map(item->{
            item.setDate(new Date());
            item.setUserId(userId);
            item.setStatus(SystemConstants.PAID);
            return item;
        }).collect(Collectors.toList());
        boolean b = orderService.saveBatch(list);
        if(!b){
            return Result.error(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        boolean b1 = userService.updateById(byId);
        return Result.ok(b1);
    }
}

