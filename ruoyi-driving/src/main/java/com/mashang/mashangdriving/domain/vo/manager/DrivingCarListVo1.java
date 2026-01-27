package com.mashang.mashangdriving.domain.vo.manager;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DrivingCarListVo1 {

  private Long carId;
  @ApiModelProperty(value = "车辆名字")
  private String carName;
  @ApiModelProperty(value = "车辆品牌")
  private String carBrand;
  @ApiModelProperty(value = "购买日期")
  private Date buyDate;
  @ApiModelProperty(value = "年检到期")
  private Date motExpires;
  @ApiModelProperty(value = "车辆可载人数")
  private Long fullPersion;
  @ApiModelProperty(value = "车牌号")
  private String plateNumber;
  @ApiModelProperty(value = "车辆状态")
  private String status;
  @ApiModelProperty(value = "车辆类型")
  private String carType;


}
