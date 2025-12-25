package com.mashang.mashangdriving.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingBillRecord {
    @TableId(type = IdType.AUTO)
  private Long recordId;
  private Long payId;
  private Long userId;
  private Long chargeLtemId;
  private Long roleId;
  private String delFlag;
}
