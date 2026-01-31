package com.mashang.mashangdriving.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DrivingInstructorStudent {
  @ApiModelProperty(value = "教练ID")
  private Long instructorId;

  @ApiModelProperty(hidden = true)
  private Long studentId;
  @ApiModelProperty(hidden = true)
  private String delFlag;
  @ApiModelProperty(value = "科目ID")
  private Long subjectId;

}
