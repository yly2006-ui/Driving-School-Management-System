package com.mashang.mashangdriving.domain.vo.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("首页--数据概览响应参数")
public class DataOverviewDtlVo {

    @ApiModelProperty("总学员数")
    private int studentNumber;

    @ApiModelProperty("在学学员")
    private int learnStudent; //状态为正常的学员

    @ApiModelProperty("本月收入")
    private Double totalRevenue;

    @ApiModelProperty("待处理预约")
    private int pendingAppointments;

    @ApiModelProperty("总学员数较上个月增加/减少百分数")
    private Object lastMonthStudentNumber;

    @ApiModelProperty("在学学员较上个月增加/减少百分数")
    private int lastMonthLearnStudent; //状态为正常的学员

    @ApiModelProperty("本月收入较上个月增加/减少百分数")
    private Double lastMonthTotalRevenue;

    @ApiModelProperty("待处理预约较昨日增加/减少百分数")
    private int lastDayPendingAppointments;
}
