package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

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
