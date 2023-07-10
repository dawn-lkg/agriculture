package com.example.agriculture.controller;



import com.example.agriculture.constants.SystemConstants;
import com.example.agriculture.entity.Comment;
import com.example.agriculture.entity.Orders;
import com.example.agriculture.entity.Result;
import com.example.agriculture.service.CommentService;
import com.example.agriculture.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (Comment)表控制层
 *
 * @author 陈黎明
 * @since 2023-04-16 10:18:39
 */
@RestController
@RequestMapping("comment")
@Api(value = "评论接口")
public class CommentController {
    /**
     * 服务对象
     */
    @Resource
    private CommentService commentService;
    @Autowired
    private OrderService orderService;
    @ApiOperation(value="增加评论")
    @PostMapping("add")
    @Transactional
    public Result add(@RequestBody Comment comment){
        Orders byId = orderService.getById(comment.getOrderId());
        BeanUtils.copyProperties(byId,comment,new String[]{"id"});
        comment.setCreateTime(new Date());
        boolean save = commentService.save(comment);
        if(save){
            byId.setStatus(SystemConstants.COMPLETE);
            orderService.updateById(byId);
        }
        return Result.ok(save);
    }

}

