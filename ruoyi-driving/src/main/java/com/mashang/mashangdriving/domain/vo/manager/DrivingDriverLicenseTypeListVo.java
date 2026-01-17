package com.mashang.mashangdriving.domain.vo.manager;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DrivingDriverLicenseTypeListVo {

    @ApiModelProperty(value = "驾驶证类型ID")
    private Long driverLicenseId;

    @ApiModelProperty(value = "驾驶代码")
    private String driverLicenseCode;

    @ApiModelProperty(value = "驾驶证类型名称")
    private String driverLicenseName;

    @ApiModelProperty(value = "培训周期")
    private String trainingCycle;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "学习难度")
    private String learningDifficulty;

    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "状态名称")
    private String statusName;


}
