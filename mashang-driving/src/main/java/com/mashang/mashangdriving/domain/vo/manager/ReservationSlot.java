package com.mashang.mashangdriving.domain.vo.manager;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("教练时间安排参数")
public class ReservationSlot {

    @ApiModelProperty("教练id")
    private Long instructorId;

    @ApiModelProperty("教练可预约开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date schedulableTimeStart;

    @ApiModelProperty("教练可预约结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date schedulableTimeEnd;

    @ApiModelProperty("教练不可预约开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date noTimeStart;

    @ApiModelProperty("教练不可预约结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date noTimeEnd;
}
