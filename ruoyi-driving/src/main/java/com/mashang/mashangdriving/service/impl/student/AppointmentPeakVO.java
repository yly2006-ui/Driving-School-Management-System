package com.mashang.mashangdriving.service.impl.student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("本周预约高峰返回参数")
public class AppointmentPeakVO {
    /** 周几 */

    @ApiModelProperty("周几")
    private String week;

    /** 时间段 09:00-11:00 */
    @ApiModelProperty("时间段(例如09:00-11:00)")
    private String timeRange;

    /** 预约次数 */
    @ApiModelProperty("预约次数")
    private Integer count;
}
