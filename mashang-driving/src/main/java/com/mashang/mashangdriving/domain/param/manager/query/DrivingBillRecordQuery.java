package com.mashang.mashangdriving.domain.param.manager.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class DrivingBillRecordQuery {
    @ApiModelProperty(value = "用户名称", example = "张三")
    private String userName;

//    @DateTimeFormat(pattern = "yyyy-MM-dd ")
//    @ApiModelProperty(value = "开始时间", example = "2024-01-10")
//    private Date beginTime;
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd ")
//    @ApiModelProperty(value = "结束时间", example = "2024-01-15 ")
//    private Date endTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd ")
    @ApiModelProperty(value = "开始时间", example = "2024-01-10")
    private String beginTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd ")
    @ApiModelProperty(value = "结束时间", example = "2024-01-15 ")
    private String endTime;

    @ApiModelProperty(value = "用户角色id", example = "102")
    private String roleId;

    @ApiModelProperty(value = "支付方式", example = "0")
    private String paymentMethod;
}