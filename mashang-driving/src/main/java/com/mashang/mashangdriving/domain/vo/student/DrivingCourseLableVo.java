package com.mashang.mashangdriving.domain.vo.student;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DrivingCourseLableVo {
    @ApiModelProperty(value = "标签ID", example = "1", required = true)
    private Integer lableId;

    @ApiModelProperty(value = "标签名称", example = "道路交通安全法规", required = true)
    private String lableName;

    private List<DrivingCourseContextVo>list;
}
