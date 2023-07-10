package com.example.agriculture.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.agriculture.constants.SystemConstants;
import com.example.agriculture.entity.Like;
import com.example.agriculture.entity.Result;
import com.example.agriculture.entity.Videolike;
import com.example.agriculture.service.VideolikeService;
import com.example.agriculture.utils.JwtTokenUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (Videolike)表控制层
 *
 * @author 陈黎明
 * @since 2023-04-02 19:20:47
 */
@RestController
@RequestMapping("videolike")
public class VideolikeController{
    /**
     * 服务对象
     */
    @Resource
    private VideolikeService videolikeService;
    @GetMapping("/count")
    public Result getNums(Integer videoId){
        LambdaQueryWrapper<Videolike> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Videolike::getVideoId,videoId);
        lambdaQueryWrapper.eq(Videolike::getStatus,SystemConstants.LIKE);
        long count = videolikeService.count(lambdaQueryWrapper);
        return  Result.ok(count);
    }
    @GetMapping("islike")
    public Result isList(Integer videoId,HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        Integer id= Integer.valueOf(JwtTokenUtil.parseToken(token));
        LambdaQueryWrapper<Videolike> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Videolike::getUserId,id);
        lambdaQueryWrapper.eq(Videolike::getVideoId,videoId);
        lambdaQueryWrapper.eq(Videolike::getStatus,SystemConstants.LIKE);
        Videolike one = videolikeService.getOne(lambdaQueryWrapper);
        if(one==null){
            return Result.ok(false);
        }
        return Result.ok(true);
    }
    @PutMapping("update")
    public Result update(@RequestBody Videolike like,HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        Integer id= Integer.valueOf(JwtTokenUtil.parseToken(token));
        Integer videoId=like.getVideoId();
        LambdaQueryWrapper<Videolike> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Videolike::getVideoId,videoId);
        lambdaQueryWrapper.eq(Videolike::getUserId,id);
        Videolike one = videolikeService.getOne(lambdaQueryWrapper);
        if(one==null){
            Videolike videolike = new Videolike();
            videolike.setVideoId(videoId);
            videolike.setUserId(id);
            videolike.setCreateTime(new Date());
            boolean save = videolikeService.save(videolike);
            return Result.ok(save);
        }
        if (one.getStatus() == SystemConstants.LIKE) {
            one.setStatus(SystemConstants.NOT_LIKE);
        } else {
            one.setStatus(SystemConstants.LIKE);
        }
        boolean b = videolikeService.updateById(one);
        return Result.ok(b);
    }
}

