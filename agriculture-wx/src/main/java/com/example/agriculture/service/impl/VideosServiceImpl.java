package com.example.agriculture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.agriculture.dao.VideosDao;
import com.example.agriculture.entity.Videos;
import com.example.agriculture.service.VideosService;
import org.springframework.stereotype.Service;

/**
 * (Videos)表服务实现类
 *
 * @author 陈黎明
 * @since 2023-03-20 18:04:27
 */
@Service("videosService")
public class VideosServiceImpl extends ServiceImpl<VideosDao, Videos> implements VideosService {

}

