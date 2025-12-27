package com.mashang.mashangdriving.domain.param.manager.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DrivingCourseQuery {
    @ApiModelProperty(value = "课程状态", example = "0")
    private String status;
    @ApiModelProperty(value = "课程类型（0=理论课程，1=实践课程）", example = "0")
    private String type;
    @ApiModelProperty(value = "课程名称", example = "科目一理论课程")
    private String courseName;
}
