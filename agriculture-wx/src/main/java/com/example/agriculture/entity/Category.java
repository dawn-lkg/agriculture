package com.example.agriculture.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Category)表实体类
 *
 * @author 陈黎明
 * @since 2023-02-03 16:57:17
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category extends Model<Category> {
    //分类id
    private Integer id;
    //分类名字
    private String name;
    //分类图片
    private String pic;
}

