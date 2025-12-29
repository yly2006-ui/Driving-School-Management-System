package com.mashang.mashangdriving.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "课程表")
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
