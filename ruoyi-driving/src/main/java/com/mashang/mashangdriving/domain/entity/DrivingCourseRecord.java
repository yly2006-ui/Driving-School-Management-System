package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingCourseRecord {

    @TableId(type = IdType.AUTO)
    private Long recordId;
    private Long userId;
    private Long contentId;
    private String finishedHours;
    private String status;
    private String delFlag;
    private Long studentId;
}
