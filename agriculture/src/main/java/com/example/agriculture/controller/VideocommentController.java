package com.example.agriculture.controller;




import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.agriculture.entity.Result;
import com.example.agriculture.entity.User;
import com.example.agriculture.entity.Videocomment;
import com.example.agriculture.entity.vo.VideoCommentVo;
import com.example.agriculture.service.UserService;
import com.example.agriculture.service.VideocommentService;
import com.example.agriculture.utils.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Videocomment)表控制层
 *
 * @author 陈黎明
 * @since 2023-04-01 16:46:10
 */
@RestController
@RequestMapping("/videocomment")
public class VideocommentController {
    /**
     * 服务对象
     */
    @Resource
    private VideocommentService videocommentService;
    @Autowired
    private UserService userService;
    @GetMapping("{id}")
    public Result getComment(@PathVariable Integer id){
        LambdaQueryWrapper<Videocomment> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Videocomment::getVideoId,id);
        lambdaQueryWrapper.isNull(Videocomment::getParentCommentId);
        List<VideoCommentVo> collect = videocommentService.list(lambdaQueryWrapper).stream().map(item -> {
            VideoCommentVo videoCommentVo = new VideoCommentVo();
            BeanUtils.copyProperties(item, videoCommentVo);
            User byId = userService.getById(item.getUserId());
            BeanUtils.copyProperties(byId, videoCommentVo,new String[]{"id"});
            LambdaQueryWrapper<Videocomment> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
            lambdaQueryWrapper1.isNotNull(Videocomment::getParentCommentId);
            lambdaQueryWrapper1.eq(Videocomment::getParentCommentId,item.getId());
            long count = videocommentService.count(lambdaQueryWrapper1);
            System.out.println(count);
            if(count>0) {
                LambdaQueryWrapper<Videocomment> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
                lambdaQueryWrapper2.eq(Videocomment::getParentCommentId, item.getId());
                List<VideoCommentVo> videoCommentVos = videocommentService.list(lambdaQueryWrapper2).stream().map(item2 -> {
                    VideoCommentVo videoCommentVo2 = new VideoCommentVo();
                    BeanUtils.copyProperties(item2, videoCommentVo2);
                    User byId2 = userService.getById(item2.getUserId());
                    BeanUtils.copyProperties(byId2, videoCommentVo2,new String[]{"id"});
                    return videoCommentVo2;
                }).collect(Collectors.toList());
                videoCommentVo.setChildren(videoCommentVos);
            }
            return videoCommentVo;
        }).collect(Collectors.toList());

        return Result.ok(collect);
    }
    @PostMapping("addComment")
    public Result addComment(@RequestBody Videocomment videocomment, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        Integer id= Integer.valueOf(JwtTokenUtil.parseToken(token));
        videocomment.setUserId(id);
        videocomment.setCreateTime(new Date());
        return Result.ok(videocommentService.save(videocomment));
    }
    @GetMapping("/count")
    public Result getNum(Integer videoId){
        LambdaQueryWrapper<Videocomment> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Videocomment::getVideoId,videoId);
        long count = videocommentService.count(lambdaQueryWrapper);
        return Result.ok(count);
    }


}

