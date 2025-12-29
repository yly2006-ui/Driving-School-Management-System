package com.mashang.mashangdriving.domain.vo.student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("所有教练信息响应参数")
public class AllInstructorListVo {

    @ApiModelProperty("教练id")
    private Long instructorId;

    @ApiModelProperty("教练名称")
    private String instructorName;

    @ApiModelProperty("教学经验")
    private String teachingYears;

    @ApiModelProperty("擅长科目")
    private String goodSubject;

    @ApiModelProperty("教练头像")
    private String photo;

//    @ApiModelProperty("车辆id")
//    private Long carId;
//
//    @ApiModelProperty("车名")
//    private String carName;
//
//    @ApiModelProperty("颜色")
//    private String carColor;
//
//    @ApiModelProperty("车辆品牌")
//    private String carBrand;
//
//    @ApiModelProperty("车牌号")
//    private String plateNumber;
//
//    @ApiModelProperty("车辆状态(0 可用,1 维修中,2 保养中,3 报废)")
//    private String status;



}
