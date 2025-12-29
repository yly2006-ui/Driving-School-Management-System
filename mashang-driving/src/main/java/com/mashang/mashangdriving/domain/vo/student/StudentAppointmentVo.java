package com.mashang.mashangdriving.domain.vo.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("添加预约响应参数")
public class StudentAppointmentVo {

    @ApiModelProperty("教练id")
    private Long instructorId;

    @ApiModelProperty("教练名称")
    private String instructorName;

    @ApiModelProperty("教学经验")
    private String teachingYears;

    @ApiModelProperty("擅长科目")
    private String goodSubject;

    @ApiModelProperty("教练头像")
    private String photo;

    @ApiModelProperty("车辆id")
    private Long carId;

    @ApiModelProperty("车名")
    private String carName;

    @ApiModelProperty("颜色")
    private String carColor;

    @ApiModelProperty("车辆品牌")
    private String carBrand;

    @ApiModelProperty("车牌号")
    private String plateNumber;

    @ApiModelProperty("车辆状态(0 可用,1 维修中,2 保养中,3 报废)")
    private String status;

    @ApiModelProperty("科目id")
    private Long subjectId;

    @ApiModelProperty("科目姓名")
    private String subjectName;

    @ApiModelProperty("预约开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty("预约结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date endTime;

}
