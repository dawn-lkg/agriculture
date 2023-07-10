package com.example.agriculture.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.agriculture.entity.AppHttpCodeEnum;
import com.example.agriculture.entity.Result;
import com.example.agriculture.entity.User;
import com.example.agriculture.service.UserService;
import com.example.agriculture.utils.JwtTokenUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (User)表控制层
 *
 * @author 陈黎明
 * @since 2023-02-26 10:56:58
 */
@RestController
@RequestMapping("user")
public class UserController  {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        System.out.println(user);
        Integer id=null;
        if(user==null) return Result.error(AppHttpCodeEnum.SYSTEM_ERROR);
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getOpenid,user.getOpenid());
        User one = userService.getOne(lambdaQueryWrapper);
        if(one==null){
            boolean save = userService.save(user);
            if(!save) return Result.error(AppHttpCodeEnum.SYSTEM_ERROR);
            id=user.getId();
        }else {
            id = one.getId();
        }
        return Result.ok(JwtTokenUtil.generateToken(String.valueOf(id)));
    }
    @GetMapping("/info")
    public Result getInfo(HttpServletRequest httpServletRequest){
        String cookie = httpServletRequest.getHeader("token");
        System.out.println(cookie);
        String s = JwtTokenUtil.parseToken(cookie);
        User byId = userService.getById(Integer.valueOf(s));
        return Result.ok(byId);
    }
    @PostMapping("/recharge")
    public Result recharge(@RequestBody User user,HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String s = JwtTokenUtil.parseToken(token);
        User byId = userService.getById(Integer.valueOf(s));
        byId.setBalance(user.getBalance()+byId.getBalance());
        boolean b = userService.updateById(byId);
        if(!b) return Result.error(AppHttpCodeEnum.SYSTEM_ERROR);
        return Result.ok(b);
    }
}

