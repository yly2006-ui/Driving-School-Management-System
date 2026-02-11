package com.mashang.mashangdriving.domain.vo.student;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MyInstructorVo {
    @ApiModelProperty(value = "学员ID")
    private Long studentId;
    @ApiModelProperty(value = "教练ID")
    private Long instructorId;
    @ApiModelProperty(value = "教练名字")
    private String instructorName;
    @ApiModelProperty(value = "教练电话")
    private String phone;
    @ApiModelProperty(value = "教练证号")
    private String instructorNo;
    @ApiModelProperty(value = "教练状态")
    private String status;
    @ApiModelProperty(value = "教学年限")
    private String teachingYears;
    @ApiModelProperty(value = "擅长科目")
    private String goodSubject;
//    @ApiModelProperty(value = "可预约开始时间")
//    private Date schedulableTimeStart;
//    @ApiModelProperty(value = "可预约结束时间")
//    private Date schedulableTimeEnd;
//    @ApiModelProperty(value = "可预约开始时间")
//    private Date noTimeStart;
//    @ApiModelProperty(value = "不可预约开始时间")
//    private Date noTimeEnd;
    @ApiModelProperty(value = "教练评分")
    private Double score;
    @ApiModelProperty(value = "车辆ID")
    private Long carId;
    @ApiModelProperty(value = "车牌号")
    private String plateNumber;
    @ApiModelProperty(value = "学员数")
    private Long studentNum;
}
