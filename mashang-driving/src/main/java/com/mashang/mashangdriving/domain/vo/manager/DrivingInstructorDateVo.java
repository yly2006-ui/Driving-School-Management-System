package com.mashang.mashangdriving.domain.vo.manager;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mashang.mashangdriving.domain.entity.DrivingCar;
import com.mashang.mashangdriving.domain.entity.DrivingRating;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DrivingInstructorDateVo {

  @ApiModelProperty(value = "教练ID")
  private Long instructorId;
  @ApiModelProperty(value = "教练名字")
  private String instructorName;
  @ApiModelProperty(value = "教练状态")
  private String status;
  @ApiModelProperty(value = "可预约开始时间")
  private Date schedulableTimeStart;
  @ApiModelProperty(value = "可预约结束时间")
  private Date schedulableTimeEnd;
  @ApiModelProperty(value = "可预约开始时间")
  private Date noTimeStart;
  @ApiModelProperty(value = "不可预约开始时间")
  private Date noTimeEnd;

}
