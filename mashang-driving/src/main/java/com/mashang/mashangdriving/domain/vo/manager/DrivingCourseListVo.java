package com.mashang.mashangdriving.domain.vo.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 驾驶课程列表VO
 */
@Data
@ApiModel(value = "DrivingCourseListVo", description = "驾驶课程列表响应对象")
public class DrivingCourseListVo {

    @ApiModelProperty(value = "课程ID", example = "1")
    private Integer courseId;

    @ApiModelProperty(value = "课程名称", example = "科目一理论课程")
    private String courseName;

    @ApiModelProperty(value = "总课时", example = "10")
    private String allHours;

    @ApiModelProperty(value = "课程状态（0=进行中，1=已结束）", example = "0")
    private String status;
}