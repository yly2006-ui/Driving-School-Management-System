package com.mashang.mashangdriving.domain.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

@Data
public class DrivingCourse {

    @TableId(type = IdType.AUTO)
    private Long courseId;
    private String courseName;
    private String allHours;
    private String type;
    private String delFlag;
    private String status;
    private String personCount;
}
