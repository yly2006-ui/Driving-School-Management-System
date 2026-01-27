package com.mashang.mashangdriving.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DrivingInstructor {

  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "教练ID")
  private Long instructorId;
  @ApiModelProperty(value = "用户ID")
  private Long userId;
  @ApiModelProperty(value = "教练名字")
  private String instructorName;
  @ApiModelProperty(value = "教练电话")
  private String phone;
  @ApiModelProperty(value = "教练身份证ID")
  private String idNumber;
  @ApiModelProperty(value = "入职时间")
  private Date entryDate;
  @ApiModelProperty(value = "教练证号")
  private String instructorNo;
  @ApiModelProperty(value = "教练状态")
  private String status;
  @ApiModelProperty(value = "教学年限")
  private String teachingYears;
  @ApiModelProperty(value = "擅长科目")
  private String goodSubject;
  @ApiModelProperty(value = "可预约开始时间")
  private Date schedulableTimeStart;
  @ApiModelProperty(value = "可预约结束时间")
  private Date schedulableTimeEnd;
  @ApiModelProperty(value = "可预约开始时间")
  private Date noTimeStart;
  @ApiModelProperty(value = "不可预约开始时间")
  private Date noTimeEnd;
  @ApiModelProperty(value = "教练照片")
  private String photo;
  @ApiModelProperty(value = "教练证件")
  private String certificate;
  @ApiModelProperty(value = "删除标志")
  private String delFlag;
  @ApiModelProperty(value = "教练评分")
  private Double score;
  @ApiModelProperty(value = "评价内容")
  private String content;
  @TableField(exist = false)
  @ApiModelProperty(value = "学员评价")
  private DrivingRating rating;
  @TableField(exist = false)
  @ApiModelProperty(value = "车辆信息")
  private DrivingCar drivingCar;


}
