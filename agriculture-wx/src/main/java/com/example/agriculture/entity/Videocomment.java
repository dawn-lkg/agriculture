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
 * (Videocomment)表实体类
 *
 * @author 陈黎明
 * @since 2023-04-12 21:59:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
public class Videocomment extends Model<Videocomment> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private Integer videoId;
    
    private String content;
    
    private Integer userId;
    
    private Date createTime;
    
    private Integer parentCommentId;
    }

