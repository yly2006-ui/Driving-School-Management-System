package com.mashang.mashangdriving.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DrivingInstructorStatus {

    @ApiModelProperty(value = "教练数")
    private Long instructorNum;
    @ApiModelProperty(value = "在职教职工")
    private Long onJobNuml;
    @ApiModelProperty(value = "平均评分")
    private Double ratingAvg;
    @ApiModelProperty(value = "平均工作量")
    private String averageWorkload;


}
