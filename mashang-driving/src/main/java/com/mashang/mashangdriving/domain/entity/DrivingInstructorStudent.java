package com.mashang.mashangdriving.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DrivingInstructorStudent {

  private Long instructorId;
  private String studentId;
  private String delFlag;
  private int subjectId;

}
