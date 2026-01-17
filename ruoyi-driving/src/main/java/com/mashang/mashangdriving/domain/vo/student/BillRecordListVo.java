package com.mashang.mashangdriving.domain.vo.student;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BillRecordListVo {

    @ApiModelProperty("总缴费金额")
    private double totalPaymentAmount;

    @ApiModelProperty("缴费次数")
    private int paymentCount;

    @ApiModelProperty("缴费详情集合")
    private List<PayDtlVo> payDtlVoList;


}
