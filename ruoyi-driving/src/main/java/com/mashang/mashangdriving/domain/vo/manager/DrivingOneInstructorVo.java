package com.mashang.mashangdriving.domain.vo.manager;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mashang.mashangdriving.domain.entity.DrivingCar;
import com.mashang.mashangdriving.domain.entity.DrivingRating;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DrivingOneInstructorVo {

  @ApiModelProperty(value = "教练ID")
  private Long instructorId;
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
  @ApiModelProperty(value = "教练照片")
  private String photo;
  @ApiModelProperty(value = "教练证件")
  private String certificate;
  @ApiModelProperty(value = "删除标志")
  private String delFlag;
  @ApiModelProperty(value = "车辆信息")
  private DrivingCar drivingCar;
  @ApiModelProperty(value = "学生数量")
  private Long studentNum;
  @ApiModelProperty(value = "总评价数")
  private Long ratingNum;
  @ApiModelProperty(value = "综合评分")
  private Double overAllRating;
  @ApiModelProperty(value = "驾驶代码")
  private String driverLicenseCode;
  @ApiModelProperty(value = "驾驶证类型名称")
  private String driverLicenseName;



}
