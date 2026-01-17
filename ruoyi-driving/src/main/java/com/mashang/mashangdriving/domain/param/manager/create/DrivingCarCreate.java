package com.mashang.mashangdriving.domain.param.manager.create;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class DrivingCarCreate {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "车辆ID",hidden = true)
    private Long carId;
    @ApiModelProperty(value = "教练ID",required = true)
    private Long instructorId;
    @NotBlank(message = "车辆名称不能为空")
    @Size(min = 2, max = 50, message = "车辆名称长度2-50")
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
    @ApiModelProperty(value = "车辆类型",required = true)
    private String carType;

}
