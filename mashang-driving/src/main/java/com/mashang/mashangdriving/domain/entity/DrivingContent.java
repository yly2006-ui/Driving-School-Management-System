package com.mashang.mashangdriving.domain.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

@Data
public class DrivingContent {

    @TableId(type = IdType.AUTO)
    private Long contentId;
    private Long lableId;
    private String contectName;
    private String courseContect;
    private String ipstructionalVideo;
    private String contentTime;
    private String delFlag;
}
