package com.mashang.mashangdriving.domain.vo.manager;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 账单记录列表VO
 */
@Data
@ApiModel(description = "账单记录列表返回对象")
public class DrivingBillRecordListVo {

    @ApiModelProperty(value = "财务记录ID", example = "1")
    private Long recordId;

    @ApiModelProperty(value = "用户名称", example = "张三")
    private String userName;

    @ApiModelProperty(value = "收费项目", example = "科目一培训费")
    private String chargeItem;

    @ApiModelProperty(value = "金额（分）", example = "500000")
    private Long amount;

    @ApiModelProperty(value = "金额", example = "+500000或者_50000")
    private String finalAmount;

    @ApiModelProperty(value = "用户角色", example = "学生")
    private String roleName;

    @ApiModelProperty(value = "支付方式", example = "支付宝")
    private String paymentMethod;

    @ApiModelProperty(value = "支付时间", example = "2024-01-15 10:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date paymentTime;

}