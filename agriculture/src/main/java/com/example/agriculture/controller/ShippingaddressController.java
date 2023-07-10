package com.example.agriculture.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.agriculture.constants.SystemConstants;
import com.example.agriculture.entity.AppHttpCodeEnum;
import com.example.agriculture.entity.Result;
import com.example.agriculture.entity.Shippingaddress;
import com.example.agriculture.service.ShippingaddressService;
import com.example.agriculture.utils.JwtTokenUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * (Shippingaddress)表控制层
 *
 * @author 陈黎明
 * @since 2023-03-03 23:51:22
 */
@RestController
@RequestMapping("address")
public class ShippingaddressController{
    /**
     * 服务对象
     */
    @Resource
    private ShippingaddressService shippingaddressService;
    @GetMapping("/getAll")
    public Result userAddress(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        Integer id = Integer.valueOf(JwtTokenUtil.parseToken(token));
        LambdaQueryWrapper<Shippingaddress> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Shippingaddress::getUserId,id);
        List<Shippingaddress> list = shippingaddressService.list(lambdaQueryWrapper);
        return Result.ok(list);
    }
    @GetMapping("/default")
    public Result getDefault(HttpServletRequest httpServletRequest){
        Integer id = Integer.valueOf(JwtTokenUtil.parseToken(httpServletRequest.getHeader("token")));
        LambdaQueryWrapper<Shippingaddress> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Shippingaddress::getUserId,id);
        lambdaQueryWrapper.eq(Shippingaddress::getIsDefault, SystemConstants.ADDRESS_DEFAULT);
        Shippingaddress one = shippingaddressService.getOne(lambdaQueryWrapper);
        return Result.ok(one);
    }
    @PutMapping("/default")
    @Transactional
    public Result setDefault(HttpServletRequest httpServletRequest,@RequestBody Shippingaddress address){
        Integer addressId = address.getId();
        System.out.println("address:"+addressId);
        Integer id = Integer.valueOf(JwtTokenUtil.parseToken(httpServletRequest.getHeader("token")));
        LambdaQueryWrapper<Shippingaddress> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Shippingaddress::getUserId,id);
        Shippingaddress shippingaddress = new Shippingaddress();
        shippingaddress.setIsDefault(SystemConstants.ADDRESS_NOT_DEFAULT);
        boolean deg=shippingaddressService.update(shippingaddress,lambdaQueryWrapper);
        if(!deg){
            return Result.error(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        lambdaQueryWrapper.eq(Shippingaddress::getId,addressId);
        shippingaddress.setIsDefault(SystemConstants.ADDRESS_DEFAULT);
        boolean update = shippingaddressService.update(shippingaddress,lambdaQueryWrapper);
        return Result.ok(update);
    }
    @PostMapping("/add")
    public Result addAddress(@RequestBody Shippingaddress shippingaddress,HttpServletRequest httpServletRequest){
        Integer id = Integer.valueOf(JwtTokenUtil.parseToken(httpServletRequest.getHeader("token")));
        shippingaddress.setUserId(id);
        System.out.println(shippingaddress);
        boolean save = shippingaddressService.save(shippingaddress);
        return Result.ok(save);
    }
    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id){
        return Result.ok(shippingaddressService.getById(id));
    }
    @PutMapping
    public Result update(@RequestBody Shippingaddress shippingaddress){
        return Result.ok(shippingaddressService.updateById(shippingaddress));
    }
}

