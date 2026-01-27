package com.mashang.mashangdriving.domain.param.manager.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description = "驾驶证类型信息")
public class DrivingDriverLicenseTypeUpdate {

    @ApiModelProperty(value = "驾驶证类型ID")
    private Long driverLicenseId;

    @ApiModelProperty(value = "驾驶代码")
    private String driverLicenseCode;

    @ApiModelProperty(value = "驾驶证类型名称")
    private String driverLicenseName;

    @ApiModelProperty(value = "培训周期")
    private String trainingCycle;

    @ApiModelProperty(value = "学习难度")
    private String learningDifficulty;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态")
    private String status;


}