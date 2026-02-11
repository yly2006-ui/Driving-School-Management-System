package com.mashang.mashangdriving.domain.vo.student;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DrivingCourseStudentListVo {
    @ApiModelProperty(value = "课程ID", example = "1", required = true)
    private Integer attributeId;

    @ApiModelProperty(value = "课程属性名称", example = "道路交通安全法规", required = true)
    private String attributeName;

    @ApiModelProperty(value = "课程简介", example = "道路交通安全法规是学习驾照考试的必备课程内容")
    private String Introduction;


    @ApiModelProperty(value = "总时长", example = "12小时")
    private String totalTime;

    @ApiModelProperty(value = "课程数量")
    private String courseCount;

    @ApiModelProperty(value = "学习人数")
    private String studyPersonTotal;

    @ApiModelProperty(value = "完成百分比")
    private String percentage;



    @ApiModelProperty(value = "课程标签")
    private String attributeLable;
}
