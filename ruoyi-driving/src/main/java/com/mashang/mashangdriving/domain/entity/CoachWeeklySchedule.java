package com.mashang.mashangdriving.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CoachWeeklySchedule {

  private Integer id;
  private Integer instructorId;
  private Integer weekDay;
  private Integer timeSlot;
  private Integer status;
  private Date createdAt;
  private Date updatedAt;

}
