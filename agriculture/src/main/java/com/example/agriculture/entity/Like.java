package com.example.agriculture.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Like)表实体类
 *
 * @author 陈黎明
 * @since 2023-04-02 19:01:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
public class Like extends Model<Like> {
    
    private Integer id;
    
    private Integer userId;
    
    private Integer videoId;
    
    private Date createTime;
    }

