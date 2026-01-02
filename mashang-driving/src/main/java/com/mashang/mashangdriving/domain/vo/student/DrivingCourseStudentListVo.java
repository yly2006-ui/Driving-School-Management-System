package com.mashang.mashangdriving.domain.vo.student;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DrivingCourseStudentListVo {
    @ApiModelProperty(value = "属性ID", example = "1", required = true)
    private Integer attributeId;

    @ApiModelProperty(value = "属性名称", example = "道路交通安全法规", required = true)
    private String attributeName;

    @ApiModelProperty(value = "课程简介", example = "道路交通安全法规是学习驾照考试的必备课程内容")
    private String Introduction;
}
