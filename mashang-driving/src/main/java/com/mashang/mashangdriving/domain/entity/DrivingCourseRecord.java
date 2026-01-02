package com.mashang.mashangdriving.domain.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

@Data
public class DrivingCourseRecord {

    @TableId(type = IdType.AUTO)
    private Long recordId;
    private Long userId;
    private Long contentId;
    private String finishedHours;
    private String status;
    private String delFlag;
}
