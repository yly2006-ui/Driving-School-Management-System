package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "学员课程记录表")
public class DrivingCourseRecord {

  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "课程记录ID")
  private Long recordId;
  @ApiModelProperty(value = "学员ID")
  private Long studentId;
  @ApiModelProperty(value = "课程ID")
  private Long courseId;
  @ApiModelProperty(value = "已完成学时")
  private String finishedHours;
  @ApiModelProperty(value = "学习状态")
  private String status;
  @ApiModelProperty(value = "课程记录ID")
  private String delFlag;


}
