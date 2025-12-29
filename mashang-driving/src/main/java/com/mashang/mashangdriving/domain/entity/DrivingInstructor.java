package com.mashang.mashangdriving.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
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
  private String teachingYears;
  private String goodSubject;
  private Date schedulableTimeStart;
  private Date schedulableTimeEnd;
  private Date noTimeStart;
  private Date noTimeEnd;
  private String photo;
  private String certificate;
  private String delFlag;
  private double score;
  private String content;

}
