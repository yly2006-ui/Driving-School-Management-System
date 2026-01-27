package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrivingInstructorStudentComment {

  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "评论ID")
  private Long commentId;
  @ApiModelProperty(value = "学员ID")
  private Long studentId;
  @ApiModelProperty(value = "教练ID")
  private Long instructorId;
  @ApiModelProperty(value = "评论内容")
  private String evaluationContent;
  @ApiModelProperty(value = "评分")
  private Double score;
  @ApiModelProperty(value = "评论时间")
  private Date commentDate;


}
