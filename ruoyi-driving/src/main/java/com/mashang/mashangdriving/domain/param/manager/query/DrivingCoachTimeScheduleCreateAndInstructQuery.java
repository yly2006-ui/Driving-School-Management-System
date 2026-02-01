package com.mashang.mashangdriving.domain.param.manager.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class DrivingCoachTimeScheduleCreateAndInstructQuery {
    @NotNull(message = "开始时间不可为空哦哦哦")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间", required = false) // 显式指定false，覆盖自动规则
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "结束时间不可为空oooq")
    @ApiModelProperty(value = "结束时间", required =false) // 显式指定false，覆盖自动规则
    private LocalDateTime endTime;

}
