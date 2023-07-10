package com.example.agriculture.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.agriculture.constants.SystemConstants;
import com.example.agriculture.entity.Good;
import com.example.agriculture.entity.Result;
import com.example.agriculture.service.GoodService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Good)表控制层
 *
 * @author 陈黎明
 * @since 2023-02-04 13:21:43
 */
@RestController
@RequestMapping("good")
public class GoodController  {
    /**
     * 服务对象
     */
    @Resource
    private GoodService goodService;
    @GetMapping("swiper")
    public Result getSwiper(){
        LambdaQueryWrapper<Good> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Good::getIsSwiper, SystemConstants.SWIPER);
        return Result.ok(goodService.list(lambdaQueryWrapper));
    }
    @GetMapping("hot")
    public Result getHot(){
        LambdaQueryWrapper<Good> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Good::getIsHot,SystemConstants.Hot);
        return Result.ok(goodService.list(lambdaQueryWrapper));
    }
    @GetMapping("/type")
    public Result getType(Integer id,Integer pageNum,Integer pageSize){
        LambdaQueryWrapper<Good> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Good::getCategoryId,id);
        Page<Good> page = goodService.page(new Page<>(pageNum - 1, pageSize), lambdaQueryWrapper);
        return Result.ok(page);
    }
    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id){
        return Result.ok(goodService.getById(id));
    }
    @GetMapping("/list/search")
    public Result getSearch(Integer pageNum,Integer pageSize,String query,Integer low,Integer height,boolean isSale){
        LambdaQueryWrapper<Good> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(isSale,Good::getSales);
        lambdaQueryWrapper.like(query!=null,Good::getName,query);
        lambdaQueryWrapper.ge(low!=null,Good::getPrice,low);
        lambdaQueryWrapper.lt(height!=null,Good::getPrice,height);
        Page<Good> page = goodService.page(new Page<>(pageNum, pageSize), lambdaQueryWrapper);
        return Result.ok(page);
    }
}

