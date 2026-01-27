package com.mashang.mashangdriving.domain.vo.manager;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "DrivingBillYearMessageVo", description = "财务年度统计数据返回对象")
public class DrivingBillYearMessageVo {

    @Excel(name = "年度总收入")
    @ApiModelProperty(value = "年度总收入", example = "3,286,420")
    private String annualTotalIncome;

    @Excel(name = "年度总收入增长率")
    @ApiModelProperty(value = "年度总收入增长率", example = "3,286,420%")
    private String annualTotalIncomelastYear;

    @Excel(name = "年度总支出")
    @ApiModelProperty(value = "年度总支出", example = "1,524,680")
    private String annualTotalExpenditure;

    @Excel(name = "年度总支出增长率")
    @ApiModelProperty(value = "年度总支出增长率", example = "1,524,680%")
    private String annualTotalExpenditurelastYear;

    @Excel(name = "年度净利润")
    @ApiModelProperty(value = "年度净利润", example = "1,761,740")
    private String annualNetProfit;

    @Excel(name = "年度净利润增长率")
    @ApiModelProperty(value = "年度净利润增长率", example = "1,761,740%")
    private String annualNetProfitlastYear;

    @Excel(name = "学员总数")
    @ApiModelProperty(value = "学员总数", example = "1,245")
    private String totalStudents;

    @Excel(name = "学员总数增长率")
    @ApiModelProperty(value = "学员总数增长率", example = "1,245%")
    private String totalStudentslastYear;

    @Excel(name = "完成培训数")
    @ApiModelProperty(value = "完成培训数", example = "986")
    private String completedTraining;

    @Excel(name = "完成培训率")
    @ApiModelProperty(value = "完成培训率", example = "986")
    private String completedTrainingFinsh;

    @Excel(name = "利润率")
    @ApiModelProperty(value = "利润率", example = "53.6%")
    private String profitMargin;

    @Excel(name = "利润率增长率")
    @ApiModelProperty(value = "利润率增长率", example = "53.6%")
    private String profitMarginlastYear;
}