package com.mashang.mashangdriving.domain.param.manager.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrivingCarQuery {

  @ApiModelProperty(value = "车辆名字")
  private String carName;
    @ApiModelProperty(value = "车牌号")
  private String plateNumber;


}
