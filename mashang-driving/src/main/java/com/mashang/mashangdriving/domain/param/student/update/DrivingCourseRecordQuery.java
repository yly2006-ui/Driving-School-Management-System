package com.mashang.mashangdriving.domain.param.student.update;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class DrivingCourseRecordQuery {

    @ApiModelProperty(value = "内容id")
    private Long contentId;
//    @ApiModelProperty(value = "记录id")
//    private Long recordId;
    @ApiModelProperty(value = "观看时长")
    private String viewTime;
}
