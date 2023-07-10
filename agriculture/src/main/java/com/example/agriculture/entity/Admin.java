package com.example.agriculture.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Admin)实体类
 *
 * @author 陈黎明
 * @since 2023-03-21 16:56:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements Serializable {
    private static final long serialVersionUID = -16440991224934737L;
    /**
     * 管理员
     */
    private Integer id;
    /**
     * 管理员用户名
     */
    private String username;
    /**
     * 管理员密码
     */
    private String password;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 头像
     */
    private String avatar;

}

