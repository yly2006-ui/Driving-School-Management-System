package com.mashang.mashangdriving.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class DrivingStudent {

    @TableId(type = IdType.AUTO)
  private Long studentId;
  private Long userId;
  private String studentName;

  private Long subjectId;
  private Long driverLicenseId;
  private String phone;
  private String emergencyPhone;
  private String status;
  private String idNumber;
  private String idNumberFront;
  private String idNumberBack;
  private String delFlag;
  private Date createTime;
  private String avatar;
  private String studentCode;
}
