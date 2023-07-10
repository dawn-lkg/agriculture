package com.example.agriculture.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.agriculture.entity.Shippingaddress;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Shippingaddress)表数据库访问层
 *
 * @author 陈黎明
 * @since 2023-03-03 23:51:22
 */
@Mapper
public interface ShippingaddressDao extends BaseMapper<Shippingaddress> {

}

