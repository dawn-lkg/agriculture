package com.example.agriculture.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author chenliming
 * @date 2023/2/25 13:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {
    private Integer id;
    //商品名字
    private String name;
    //商品价格
    private Double price;
    private Integer goodId;
    private String image;
    private Integer status;
    private Integer quantity;
    private Date date;
}
