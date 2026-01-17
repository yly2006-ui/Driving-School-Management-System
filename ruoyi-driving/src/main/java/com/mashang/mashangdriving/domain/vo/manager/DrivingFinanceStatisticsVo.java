package com.mashang.mashangdriving.domain.vo.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "DrivingFinanceStatisticsVo", description = "驾驶培训财务统计月数据返回对象")
public class DrivingFinanceStatisticsVo {

    // ============== 第一行：总收入相关 ==============
    @ApiModelProperty(value = "总收入金额（大数字）", example = "286420")
    private String annualTotalIncome;

    @ApiModelProperty(value = "总收入较上月增长率（百分比，带符号）", example = "15%")
    private String totalIncomeGrowthRate;

    // ============== 第二行：学员缴费相关 ==============
    @ApiModelProperty(value = "学员缴费金额（大数字）", example = "245800")
    private String studentTuitionFees;

    @ApiModelProperty(value = "学员缴费占总收入比例（百分比）", example = "86%")
    private String studentFeesPercentage;

    // ============== 第三行：总支出相关 ==============
    @ApiModelProperty(value = "总支出金额（大数字）", example = "124680")
    private String annualTotalExpenditure;

    @ApiModelProperty(value = "总支出较上月增长率（百分比，带符号）", example = "-8%")
    private String totalExpenditureGrowthRate;

    // ============== 第四行：其他收入相关 ==============
    @ApiModelProperty(value = "其他收入金额（大数字）", example = "40620")
    private String otherOperatingIncome;

    @ApiModelProperty(value = "其他收入占总收入比例（百分比）", example = "14%")
    private String otherIncomePercentage;

    // ============== 第五行：净利润相关 ==============
    @ApiModelProperty(value = "净利润金额（大数字）", example = "161740")
    private String annualNetProfit;

    @ApiModelProperty(value = "净利润较上月增长率（百分比，带符号）", example = "23%")
    private String netProfitGrowthRate;

    // ============== 第六行：平均客单价相关 ==============
    @ApiModelProperty(value = "平均客单价金额（大数字）", example = "3580")
    private String averageRevenue;

    @ApiModelProperty(value = "平均客单价较上月增长率（百分比，带符号）", example = "5%")
    private String averageRevenueGrowthRate;


}