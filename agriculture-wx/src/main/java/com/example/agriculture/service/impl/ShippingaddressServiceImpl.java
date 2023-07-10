package com.example.agriculture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.agriculture.dao.ShippingaddressDao;
import com.example.agriculture.entity.Shippingaddress;
import com.example.agriculture.service.ShippingaddressService;
import org.springframework.stereotype.Service;

/**
 * (Shippingaddress)表服务实现类
 *
 * @author 陈黎明
 * @since 2023-03-03 23:51:22
 */
@Service("shippingaddressService")
public class ShippingaddressServiceImpl extends ServiceImpl<ShippingaddressDao, Shippingaddress> implements ShippingaddressService {

}

