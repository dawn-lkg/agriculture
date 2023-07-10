package com.example.agriculture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.agriculture.dao.GoodDao;
import com.example.agriculture.entity.Good;
import com.example.agriculture.service.GoodService;
import org.springframework.stereotype.Service;

/**
 * (Good)表服务实现类
 *
 * @author 陈黎明
 * @since 2023-02-04 13:21:46
 */
@Service("goodService")
public class GoodServiceImpl extends ServiceImpl<GoodDao, Good> implements GoodService {

}

