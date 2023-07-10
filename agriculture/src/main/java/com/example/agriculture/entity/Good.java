package com.example.agriculture.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Good)表实体类
 *
 * @author 陈黎明
 * @since 2023-02-04 13:21:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
public class Good extends Model<Good> {
    //商品id
    private Integer id;
    //商品名字
    private String name;
    //商品价格
    private Double price;
    //商品库存
    private Integer stock;
    private Integer sales;
    //商品分类
    private Integer categoryId;
    //商品描述
    private String description;
    //是否热卖
    @JsonIgnore
    private Integer isHot;
    //是否轮播
    @JsonIgnore
    private Integer isSwiper;
    //轮播图片
    private String swiperPic;
    //商品图片
    private String image;
}

