package com.example.agriculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Shippingaddress)表实体类
 *
 * @author 陈黎明
 * @since 2023-03-04 00:12:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
public class Shippingaddress extends Model<Shippingaddress> {
    //地址id
    @TableId(type = IdType.AUTO)
    private Integer id;
    //收货人姓名
    private String recipientName;
    //收货人电话
    private String recipientPhone;
    //收货人地址
    private String recipientAddress;
    //用户Id
    private Integer userId;
    //是否默认
    private Integer isDefault;
    }

