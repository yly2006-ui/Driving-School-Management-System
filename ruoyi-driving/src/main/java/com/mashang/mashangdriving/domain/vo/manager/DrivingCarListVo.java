package com.mashang.mashangdriving.domain.vo.manager;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DrivingCarListVo {

  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "车辆ID",hidden = true)
  private Long carId;
  @ApiModelProperty(value = "教练ID",hidden = true)
  private Long instructorId;
  @ApiModelProperty(value = "车辆名字",required = true)
  private String carName;
  @ApiModelProperty(value = "车辆颜色")
  private String carColor;
  @ApiModelProperty(value = "车辆品牌")
  private String carBrand;
  @ApiModelProperty(value = "购买日期")
  private Date buyDate;
  @ApiModelProperty(value = "变速箱")
  private String transmissionType;
  @ApiModelProperty(value = "发动机号")
  private String ein;
  @ApiModelProperty(value = "vin码")
  private String vin;
  @ApiModelProperty(value = "年检到期")
  private Date motExpires;
  @ApiModelProperty(value = "车辆可载人数")
  private Long fullPersion;
  @ApiModelProperty(value = "车牌号",required = true)
  private String plateNumber;
  @ApiModelProperty(value = "车辆状态")
  private String status;
  @ApiModelProperty(value = "删除标志")
  private String delFlag;
  @ApiModelProperty(value = "车辆类型")
  private String carType;


}
