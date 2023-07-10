package com.example.agriculture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.agriculture.dao.VideolikeDao;
import com.example.agriculture.entity.Videolike;
import com.example.agriculture.service.VideolikeService;
import org.springframework.stereotype.Service;

/**
 * (Videolike)表服务实现类
 *
 * @author 陈黎明
 * @since 2023-04-02 19:20:47
 */
@Service("videolikeService")
public class VideolikeServiceImpl extends ServiceImpl<VideolikeDao, Videolike> implements VideolikeService {

}

