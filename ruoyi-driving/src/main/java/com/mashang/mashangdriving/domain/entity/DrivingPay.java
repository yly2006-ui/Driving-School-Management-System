package com.mashang.mashangdriving.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
@Data
public class DrivingPay {

    @TableId(type = IdType.AUTO)
  private Long payId;
  private Long userId;
  private Long chargeLtemId;
  private String detailedExplanation;
  private String payType;
  private String delFlag;
  private Date createTime;
  private String payNo;
  private String status;
  private String billStatus;
}
