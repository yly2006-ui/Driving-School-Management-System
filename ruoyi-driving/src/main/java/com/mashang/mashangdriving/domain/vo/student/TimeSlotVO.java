package com.mashang.mashangdriving.domain.vo.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("AI 低峰时段返回")
public class TimeSlotVO {
    /**
     * 前端展示用：09:00-11:00
     */
    @ApiModelProperty("可预约时间段")
    private String timeSlot;

    /**
     * 实际开始时间（Date，用于数据库计算）
     */
    @ApiModelProperty("实际开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date startTime;

    /**
     * 实际结束时间
     */
    @ApiModelProperty("实际结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date endTime;
}
