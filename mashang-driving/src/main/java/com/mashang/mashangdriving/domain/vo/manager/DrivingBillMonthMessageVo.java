package com.mashang.mashangdriving.domain.vo.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "DrivingBillYearMessageVo", description = "财务年度统计数据返回对象")
public class DrivingBillMonthMessageVo {


            @ApiModelProperty(value = "总收入金额", example = "286420")
            private String totalIncome;
            @ApiModelProperty(value = "总收入较上月增长率", example = "+15%")
            private String incomeGrowth;

            @ApiModelProperty(value = "总支出金额", example = "124680")
            private String totalExpense;
            @ApiModelProperty(value = "总支出较上月增长率", example = "-8%")
            private String expenseGrowth;

            @ApiModelProperty(value = "净利润金额", example = "161740")
            private String netProfit;
            @ApiModelProperty(value = "净利润较上月增长率", example = "+23%")
            private String profitGrowth;

            @ApiModelProperty(value = "学员缴费金额", example = "245800")
            private String studentPayment;
            @ApiModelProperty(value = "学员缴费占比", example = "86%")
            private String paymentRatio;

            @ApiModelProperty(value = "其他收入金额", example = "40620")
            private String otherIncome;
            @ApiModelProperty(value = "其他收入占比", example = "14%")
            private String otherIncomeRatio;

            @ApiModelProperty(value = "平均客单价", example = "3580")
            private String avgCustomerPrice;
            @ApiModelProperty(value = "平均客单价较上月增长率", example = "+5%")
            private String priceGrowth;

}