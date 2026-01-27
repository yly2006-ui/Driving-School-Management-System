package com.mashang.mashangdriving.domain.param.manager.create;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DrivingInstructorCreate {

  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "教练ID", hidden = true)
  private Long instructorId;
  @ApiModelProperty(value = "用户ID",hidden = true)
  private Long userId;
  @ApiModelProperty(value = "教练名字",required = true)
  private String instructorName;
  @ApiModelProperty(value = "教练电话",required = true)
  private String phone;
  @ApiModelProperty(value = "教练身份证ID",required = true)
  private String idNumber;
  @ApiModelProperty(value = "入职时间",hidden = true)
  private Date entryDate;
  @ApiModelProperty(value = "教学年限")
  private String teachingYears;
  @ApiModelProperty(value = "擅长科目",required = true)
  private String goodSubject;
  @ApiModelProperty(value = "教练照片")
  private String photo;
  @ApiModelProperty(value = "教练证件")
  private String certificate;



}
