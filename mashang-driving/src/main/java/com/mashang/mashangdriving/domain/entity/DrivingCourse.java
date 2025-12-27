package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableField("del_flag")
    private String delFlag;
    private String status;
    private String personCount;
}
