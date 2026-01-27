package com.mashang.mashangdriving.domain.vo.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PayDtlVo {

    @ApiModelProperty("缴费项目id")
    private Long chargeLtemId;

    @ApiModelProperty("缴费项目名称")
    private String chargeLtemName;

    @ApiModelProperty("缴费金额")
    private double amount;

    @ApiModelProperty("支付方式(0 微信,1 支付宝,2 银行卡)")
    private String payType;

    @ApiModelProperty("订单号")
    private Long payId;

    @ApiModelProperty("订单号")
    private String payNo;

    @ApiModelProperty("缴费时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("支付状态(0 支付成功 1 未支付)")
    private String status;

//    @ApiModelProperty("详细说明")
//    private String detailedExplanation;
}
