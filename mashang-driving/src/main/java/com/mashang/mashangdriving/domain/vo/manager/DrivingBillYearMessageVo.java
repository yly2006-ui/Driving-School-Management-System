package com.mashang.mashangdriving.domain.vo.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "DrivingBillYearMessageVo", description = "财务年度统计数据返回对象")
public class DrivingBillYearMessageVo {

    @ApiModelProperty(value = "年度总收入", example = "3,286,420")
    private String annualTotalIncome;

    @ApiModelProperty(value = "年度总收入增长率", example = "3,286,420")
    private String annualTotalIncomelastYear;

    @ApiModelProperty(value = "年度总支出", example = "1,524,680")
    private String annualTotalExpenditure;

    @ApiModelProperty(value = "年度总支出增长率", example = "1,524,680")
    private String annualTotalExpenditurelastYear;

    @ApiModelProperty(value = "年度净利润", example = "1,761,740")
    private String annualNetProfit;

    @ApiModelProperty(value = "年度净利润增长率", example = "1,761,740")
    private String annualNetProfitlastYear;

    @ApiModelProperty(value = "学员总数", example = "1,245")
    private String totalStudents;
    @ApiModelProperty(value = "学员总数增长率", example = "1,245")
    private String totalStudentslastYear;

    @ApiModelProperty(value = "完成培训数", example = "986")
    private String completedTraining;
    @ApiModelProperty(value = "完成培训数", example = "986")
    private String completedTrainingFinsh;

    @ApiModelProperty(value = "利润率", example = "53.6%")
    private String profitMargin;
    @ApiModelProperty(value = "利润率增长率", example = "53.6%")
    private String profitMarginlastYear;
}