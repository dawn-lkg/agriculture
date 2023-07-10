package com.example.agriculture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.agriculture.dao.LikeDao;
import com.example.agriculture.entity.Like;
import com.example.agriculture.service.LikeService;
import org.springframework.stereotype.Service;

/**
 * (Like)表服务实现类
 *
 * @author 陈黎明
 * @since 2023-04-02 19:01:36
 */
@Service("likeService")
public class LikeServiceImpl extends ServiceImpl<LikeDao, Like> implements LikeService {

}

