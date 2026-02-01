package com.mashang.mashangdriving.domain.param.manager.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class DrivingCoachTimeScheduleCreate {

    @NotNull(message = "开始时间不能为空") // 核心：校验startTime不能为null
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "开始时间",required = true)
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "开始时间不能为空") // 核心：校验startTime不能为null
    @ApiModelProperty(value = "结束时间",required = true)
    private LocalDateTime endTime;

}
