package com.mashang.mashangdriving.domain.param.manager.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 驾驶课程修改
 */
@Data
@ApiModel(value = "DrivingCourseUpdate", description = "驾驶课程修改对象")
public class DrivingCourseUpdate {

    @ApiModelProperty(value = "课程ID", example = "1")
    private Integer courseId;

    @ApiModelProperty(value = "课程名称", example = "科目一理论课程")
    private String courseName;

    @ApiModelProperty(value = "课程类型（0=理论课程，1=实践课程）", example = "0")
    private String type;

    @ApiModelProperty(value = "总课时", example = "10")
    private String allHours;


    @ApiModelProperty(value = "状态", example = "0")
    private String status;

}