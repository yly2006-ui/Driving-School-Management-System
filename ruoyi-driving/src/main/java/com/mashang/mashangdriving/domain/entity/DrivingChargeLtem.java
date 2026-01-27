package com.mashang.mashangdriving.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingChargeLtem {

    @TableId(type = IdType.AUTO)
  private Long chargeLtemId;
  private String chargeLtemName;
  private double amount;
  private String delFlag;

}
