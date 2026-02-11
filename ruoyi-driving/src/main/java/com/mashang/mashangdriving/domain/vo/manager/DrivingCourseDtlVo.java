package com.mashang.mashangdriving.domain.vo.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 驾驶课程详情VO
 */
@Data
@ApiModel(value = "DrivingCourseDtlVo", description = "驾驶课程详情响应对象")
public class DrivingCourseDtlVo {

    @ApiModelProperty(value = "课程ID", example = "1")
    private Integer courseId;

    @ApiModelProperty(value = "课程名称", example = "科目一理论课程")
    private String courseName;

    @ApiModelProperty(value = "课程类型（0=理论课程，1=实践课程）", example = "0")
    private String type;

    @ApiModelProperty(value = "总课时", example = "10")
    private String allHours;

    @ApiModelProperty(value = "人数", example = "5")
    private String personCount;

    private List<DrivingChapterDtlVo>chapterDtlVos;
}