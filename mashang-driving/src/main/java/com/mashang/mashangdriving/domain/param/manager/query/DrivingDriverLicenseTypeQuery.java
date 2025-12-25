package com.mashang.mashangdriving.domain.param.manager.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class DrivingDriverLicenseTypeQuery {

    @ApiModelProperty(value = "驾驶代码")
    private String driverLicenseCode;

    @ApiModelProperty(value = "驾驶证类型名称")
    private String driverLicenseName;

    @ApiModelProperty(value = "状态(0存在,1删除)")
    private String status;

    @ApiModelProperty(value = "学习难度(数字代表星级如1既是一星难度)")
    private String learningDifficulty;


}