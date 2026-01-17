package com.mashang.mashangdriving.domain.vo.student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 驾校课程属性与内容关联实体
 */
@Data
@ApiModel(description = "驾校课程属性与内容关联信息")
public class DrivingCourseAttributeVO  {


    @ApiModelProperty(value = "属性ID", example = "1", required = true)
    private Integer attributeId;

    @ApiModelProperty(value = "属性名称", example = "道路交通安全法规", required = true)
    private String attributeName;

    @ApiModelProperty(value = "类型", example = "1", notes = "1-理论, 2-实操")
    private Integer type;

    @ApiModelProperty(value = "总时长", example = "12小时")
    private String totalTime;

    @ApiModelProperty(value = "课程数量")
    private String courseCount;

    @ApiModelProperty(value = "完成进度数量")
    private String finish;

    @ApiModelProperty(value = "学习人数")
    private String studyPersonTotal;
    private List<DrivingCourseLableVo> drivingCourseLableVoList;



}