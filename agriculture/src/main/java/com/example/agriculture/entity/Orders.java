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
 * (Orders)表实体类
 *
 * @author 陈黎明
 * @since 2023-04-15 21:34:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
public class Orders extends Model<Orders> {
    //订单id
    @TableId(type = IdType.AUTO)
    private Integer id;
    //用户id
    private Integer userId;
    //商品id
    private Integer goodId;
    //订单状态（0代表待支付，1代表一支付，2代表已发货，3代表已完成，4代表待评价
    private Integer status;
    //订单数量
    private Integer quantity;
    //订单日期
    private Date date;
    //订单地址
    private Integer addressId;
    }

