package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;


@Data
@ApiModel(description = "驾驶证类型信息")
public class DrivingDriverLicenseType {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "驾驶证类型ID")
    private Long driverLicenseId;

    @ApiModelProperty(value = "驾驶代码")
    private String driverLicenseCode;

    @ApiModelProperty(value = "驾驶证类型名称")
    private String driverLicenseName;

    @ApiModelProperty(value = "培训周期")
    private String trainingCycle;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "学习难度")
    private String learningDifficulty;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态")
    private String status;

    @TableLogic
    @ApiModelProperty(value = "删除标志")
    private String delFlag;
}