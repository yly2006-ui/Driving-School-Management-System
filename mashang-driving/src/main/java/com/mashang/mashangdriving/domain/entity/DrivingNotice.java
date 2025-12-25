package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class DrivingNotice {

    @TableId(type = IdType.AUTO)
  private Long noticeId;
  private Long roleId;
  private String title;
  private String text;
  private Date publishTime;
  private String delFlag;
  private String status;
}
