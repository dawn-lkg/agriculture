package com.example.agriculture.controller;




import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.agriculture.entity.Admin;
import com.example.agriculture.entity.Result;
import com.example.agriculture.entity.Videos;
import com.example.agriculture.entity.vo.VideoDetailVo;
import com.example.agriculture.entity.vo.VideoVo;
import com.example.agriculture.service.AdminService;
import com.example.agriculture.service.VideosService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Videos)表控制层
 *
 * @author 陈黎明
 * @since 2023-03-20 18:04:27
 */
@RestController
@RequestMapping("videos")
public class VideosController{
    /**
     * 服务对象
     */
    @Resource
    private VideosService videosService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/list")
    public Result list(Integer pageNum,Integer pageSize,String query){
        LambdaQueryWrapper<Videos> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(query!=null,Videos::getTitle,query);
        lambdaQueryWrapper.orderByDesc(Videos::getCreateTime);
        Page<Videos> page = videosService.page(new Page<Videos>(pageNum, pageSize), lambdaQueryWrapper);
        return Result.ok(page);
    }
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        VideoVo videoVo=new VideoVo();
        Videos videos = videosService.getById(id);
        Admin user = adminService.getById(videos.getUserId());
        BeanUtils.copyProperties(videos,videoVo);
        BeanUtils.copyProperties(user,videoVo);
        return Result.ok(videoVo);
    }
    @GetMapping("/detail/{id}")
    public Result getDtatilById(@PathVariable Integer id){
        VideoDetailVo vo=new VideoDetailVo();
        Videos byId = videosService.getById(id);
        Admin byId1 = adminService.getById(byId.getUserId());
        BeanUtils.copyProperties(byId,vo);
        BeanUtils.copyProperties(byId1,vo);
        return Result.ok(vo);
    }
}

