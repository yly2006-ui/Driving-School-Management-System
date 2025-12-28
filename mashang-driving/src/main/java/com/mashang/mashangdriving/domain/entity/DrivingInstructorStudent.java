package com.mashang.mashangdriving.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingInstructorStudent {

    @TableId(type = IdType.AUTO)
  private Long instructorId;
    private Long subjectId;
  private String studentId;
  private String delFlag;

}
