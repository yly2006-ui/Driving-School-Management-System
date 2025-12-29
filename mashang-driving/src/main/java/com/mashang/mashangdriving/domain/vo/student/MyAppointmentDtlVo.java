package com.mashang.mashangdriving.domain.vo.student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("所有教练信息响应参数")
public class MyAppointmentDtlVo {

    @ApiModelProperty("教练id")
    private Long instructorId;

    @ApiModelProperty("教练名称")
    private String instructorName;

    @ApiModelProperty("车辆id")
    private Long carId;

    @ApiModelProperty("车名")
    private String carName;

    @ApiModelProperty("车辆品牌")
    private String carBrand;

    @ApiModelProperty("车牌号")
    private String plateNumber;

    @ApiModelProperty(value = "地点ID")
    private Long locationId;

    @ApiModelProperty(value = "地点名称")
    private String locationName;

    @ApiModelProperty(value = "科目id")
    private Long subjectId;

    @ApiModelProperty(value = "科目名称")
    private String subjectName;

    @ApiModelProperty("预约处理状况(0 已确认 1 待审核 2 已完成 3 已取消)")
    private String status;

}
