package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class DrivingCar {

    @TableId(type = IdType.AUTO)
  private Long carId;
  private Long instructorId;
  private String carName;
  private String carColor;
  private String carBrand;
  private Date buyDate;
  private String transmissionType;
  private String ein;
  private String vin;
  private Date motExpires;
  private Long fullPersion;
  private String plateNumber;
  private String status;
  private String delFlag;

}
