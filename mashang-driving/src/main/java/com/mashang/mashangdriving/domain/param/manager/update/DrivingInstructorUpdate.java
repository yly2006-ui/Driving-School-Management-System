package com.mashang.mashangdriving.domain.param.manager.update;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DrivingInstructorUpdate {

  @ApiModelProperty(value = "教练ID")
  private Long instructorId;
  @ApiModelProperty(value = "用户ID",hidden = true)
  private Long userId;
  @ApiModelProperty(value = "教练名字")
  private String instructorName;
  @ApiModelProperty(value = "教练电话")
  private String phone;
  @ApiModelProperty(value = "教练身份证ID")
  private String idNumber;
  @ApiModelProperty(value = "入职时间",hidden = true)
  private Date entryDate;
  @ApiModelProperty(value = "教学年限")
  private String teachingYears;
  @ApiModelProperty(value = "擅长科目")
  private String goodSubject;
  @ApiModelProperty(value = "教练照片")
  private String photo;




}
