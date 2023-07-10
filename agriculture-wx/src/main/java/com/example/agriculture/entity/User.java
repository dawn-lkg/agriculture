package com.example.agriculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (User)表实体类
 *
 * @author 陈黎明
 * @since 2023-02-26 10:56:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
public class User extends Model<User> {
    //用户id
    @TableId(type = IdType.AUTO)
    private Integer id;
    //用户姓名
    private String name;
    //微信小程序的openid        
    private String openid;
    //微信小程序头像
    private String avatar;
    //用户余额
    private Double balance;

    }

