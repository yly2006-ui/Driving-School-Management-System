package com.mashang.mashangdriving.domain.vo.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

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

    @ApiModelProperty("上个月总学员数")
    private int lastMonthStudentNumber;

    @ApiModelProperty("上个月在学学员")
    private int lastMonthLearnStudent; //状态为正常的学员

    @ApiModelProperty("上个月总收入")
    private Double lastMonthTotalRevenue;

    @ApiModelProperty("昨日待处理预约")
    private int lastDayPendingAppointments;

    @ApiModelProperty("最新通知")
    private List<DataOverviewNoticeDtlVo> DataOverviewNoticeDtlVoS;
}
