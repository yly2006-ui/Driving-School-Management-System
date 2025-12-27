package com.mashang.mashangdriving.domain.vo.manager;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 驾驶课程创建请求
 */
@Data
public class DrivingCourseCreate {

    @ApiModelProperty(value = "课程名称", required = true, example = "科目一理论基础")
    private String courseName;

    @ApiModelProperty(value = "总学时", required = true, example = "40")
    private String allHours;

    @ApiModelProperty(value = "课程类型", required = true, example = "0")
    private String type;

    @ApiModelProperty(value = "课程状态", required = true, example = "0")
    private String status;
}