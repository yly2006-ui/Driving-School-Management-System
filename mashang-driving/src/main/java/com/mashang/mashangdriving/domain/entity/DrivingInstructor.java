package com.mashang.mashangdriving.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
@Data
public class DrivingInstructor {

    @TableId(type = IdType.AUTO)
  private Long instructorId;
  private Long userId;
  private String instructorName;
  private String phone;
  private String idNumber;
  private Date entryDate;
  private Long instructorNo;
  private String status;
  private Long teachingYears;
  private Date schedulableTimeStart;
  private Date schedulableTimeEnd;
  private Date noSchedulableTimeStart;
  private Date _NoSchedulableTimeEnd;
  private String photo;
  private String _InstructorCertificate;
  private String delFlag;

}
