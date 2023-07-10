package com.example.agriculture.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Videos)表实体类
 *
 * @author 陈黎明
 * @since 2023-03-20 22:52:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
public class Videos extends Model<Videos> {
    //主键，自增长
    private Integer id;
    //用户 ID
    private Integer userId;
    //视频文件的 URL 地址
    private String videoUrl;
    //视频封面的 URL 地址
    private String coverUrl;
    //视频标题
    private String title;
    //视频描述
    private String description;
    //视频上传时间
    private Date createTime;
    //视频状态（审核、正常）
    private Integer status;
    //商品id
    private Integer goodId;


    }

