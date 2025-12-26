package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingSubject {

    @TableId(type = IdType.AUTO)
  private Long subjectId;
  private String subjectName;
  private String delFlag;

}
