package com.mashang.mashangdriving.domain.vo.manager;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("管理端预约列表响应参数")
public class ManagerAppointmentListVo {

    @ApiModelProperty(value = "预约状态")
    private String status;

    @ApiModelProperty(value = "学员姓名")
    private String studentName;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "学员id")
    private Long studentId;

    @ApiModelProperty(value = "报名时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "教练id")
    private Long instructorId;

    @ApiModelProperty(value = "教练姓名")
    private String instructorName;

    @ApiModelProperty(value = "车牌号")
    private String plateNumber;

    @ApiModelProperty(value = "车辆id")
    private Long carId;

    @ApiModelProperty(value = "科目id")
    private Long subjectId;

    @ApiModelProperty(value = "科目名称")
    private String subjectName;

    @ApiModelProperty("科目标签")
    private String tag;

    @ApiModelProperty(value = "地点id")
    private Long locationId;

    @ApiModelProperty(value = "地点名称")
    private String locationName;

    @ApiModelProperty(value = "预约开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "预约结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "预约id")
    private Long appointmentId;

}
