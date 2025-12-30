package com.mashang.mashangdriving.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class DrivingAppointment {
    @TableId(type = IdType.AUTO)
  private Long appointmentId;
  private Long instructorId;
  private Long studentId;
  private Long subjectId;
  private Long locationId;
  private Date startTime;
  private Date endTime;
  private String delFlag;
  private Date createTime;
  private String status;
}
