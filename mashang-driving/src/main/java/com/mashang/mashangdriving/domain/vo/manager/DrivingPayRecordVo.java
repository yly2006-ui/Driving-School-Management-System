package com.mashang.mashangdriving.domain.vo.manager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DrivingPayRecordVo {
    @ApiModelProperty("支付ID")
    private Long payId;

    @ApiModelProperty("支付编号")
    private String payNo;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("车辆ID")
    private Long carId;

    @ApiModelProperty("车辆名称")
    private String carName;

    @ApiModelProperty("车牌号")
    private String plateNumber;

    @ApiModelProperty("收费项目ID")
    private Long chargeLtemId;

    @ApiModelProperty("收费项目名称")
    private String chargeLtemName;

    @ApiModelProperty("金额")
    private double amount;

    @ApiModelProperty("详细说明")
    private String detailedExplanation;

    @ApiModelProperty("支付类型")
    private String payType;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("删除标志")
    private String delFlag;

}
