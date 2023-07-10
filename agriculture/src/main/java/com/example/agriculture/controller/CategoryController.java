package com.example.agriculture.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.agriculture.entity.Category;
import com.example.agriculture.entity.Result;
import com.example.agriculture.entity.vo.CategoryVo;
import com.example.agriculture.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Category)表控制层
 *
 * @author 陈黎明
 * @since 2023-02-03 16:57:15
 */
@RestController
@Api(value = "分类接口")
@RequestMapping("category")
public class CategoryController {
    /**
     * 服务对象
     */
    @Resource
    private CategoryService categoryService;
    @ApiOperation(value="获取首页分类信息")
    @GetMapping("/menu")
    public Result getMenu(){
        Page<Category> page = categoryService.page(new Page<>(0, 8));
        return Result.ok(page);

    }
    @ApiOperation(value="获取全部分类信息")
    @GetMapping("/all")
    public Result getAll(){
        List<CategoryVo> collect = categoryService.list().stream().map(item -> {
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(item, categoryVo);
            return categoryVo;
        }).collect(Collectors.toList());
        return Result.ok(collect);
    }
}

