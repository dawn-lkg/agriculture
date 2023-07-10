package com.example.agriculture.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenliming
 * @date 2023/2/5 20:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo {
    private Integer id;
    //分类名字
    private String name;
}
