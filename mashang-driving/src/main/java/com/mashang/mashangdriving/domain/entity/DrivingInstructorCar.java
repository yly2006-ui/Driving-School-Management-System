package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrivingInstructorCar {

  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "车辆ID",hidden = true)
  private Long carId;
  @ApiModelProperty(value = "教练ID",hidden = true)
  private Long instructorId;
  @ApiModelProperty(value = "车辆名字",required = true)
  private String carName;
    @ApiModelProperty(value = "购买日期")
  private Date buyDate;
  @ApiModelProperty(value = "车牌号",required = true)
  private String plateNumber;
  @ApiModelProperty(value = "车辆类型")
  private String carType;
  @ApiModelProperty(value = "车辆状态",hidden = true)
  private String status;
  @ApiModelProperty(value = "删除标志",hidden = true)
  private String delFlag;



}
