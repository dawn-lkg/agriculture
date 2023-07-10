package com.example.agriculture.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author chenliming
 * @date 2023/4/2 23:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDetailVo {
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

    private String avatar;
    private String username;
}
