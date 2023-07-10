package com.example.agriculture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.agriculture.dao.VideocommentDao;
import com.example.agriculture.entity.Videocomment;
import com.example.agriculture.service.VideocommentService;
import org.springframework.stereotype.Service;

/**
 * (Videocomment)表服务实现类
 *
 * @author 陈黎明
 * @since 2023-04-01 16:46:10
 */
@Service("videocommentService")
public class VideocommentServiceImpl extends ServiceImpl<VideocommentDao, Videocomment> implements VideocommentService {

}

