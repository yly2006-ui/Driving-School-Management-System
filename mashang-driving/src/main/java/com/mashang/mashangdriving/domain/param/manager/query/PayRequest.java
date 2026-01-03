package com.mashang.mashangdriving.domain.param.manager.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayRequest {


    // 固定项目时必传
    @ApiModelProperty(value = "项目id(固定项目时必传)")
    private Long chargeItemId;

    // 自定义项目时必传
    @ApiModelProperty(value = "项目名称(自定义项目时必传)")
    private String chargeItemName;
    @ApiModelProperty(value = "金额(自定义项目时必传)")
    private Double amount;

    // 通用
    //缴费详细说明
    @ApiModelProperty(value = "缴费详细说明(通用)")
    private String detailedExplanation;
}
