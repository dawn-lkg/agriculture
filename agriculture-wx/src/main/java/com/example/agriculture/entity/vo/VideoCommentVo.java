package com.example.agriculture.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.text.View;
import java.util.List;

/**
 * @author chenliming
 * @date 2023/4/1 17:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoCommentVo {
    private Integer id;

    private Integer videoId;

    private String content;

    private String userId;

    private String name;

    private String avatar;
    private List<VideoCommentVo> children;
}
