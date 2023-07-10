package com.example.agriculture.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Comment)表实体类
 *
 * @author 陈黎明
 * @since 2023-04-16 10:18:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
public class Comment extends Model<Comment> {
    //评价ID
    @TableId(type = IdType.AUTO)
    private Integer id;
    //商品ID
    private Integer goodId;
    //订单ID
    private Integer orderId;
    //用户ID
    private Integer userId;
    //评价内容
    private String content;
    //评分
    private Integer score;
    //评价时间
    private Date createTime;

    }

