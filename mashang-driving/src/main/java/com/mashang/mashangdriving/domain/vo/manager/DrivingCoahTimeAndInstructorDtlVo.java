package com.mashang.mashangdriving.domain.vo.manager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DrivingCoahTimeAndInstructorDtlVo {
    @ApiModelProperty(value = "教练ID")
    private Long instructorId;
    @ApiModelProperty(value = "教练名字")
    private String instructorName;
    @ApiModelProperty(value = "擅长科目")
    private String goodSubject;
    @ApiModelProperty(value = "教练评分")
    private Double score;
    @ApiModelProperty(value = "当天已预约人数")
    private String person;
}
