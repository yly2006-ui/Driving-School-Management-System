package com.mashang.mashangdriving.domain.param.manager.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 地点管理实体类
 */
@Data
@ApiModel(description = "地点管理实体")
public class DrivingLocationQuery {

    @ApiModelProperty(value = "地点名称", example = "主训练场A")
    private String locationName;

    @ApiModelProperty(value = "状态", example = "0", notes = "0:正常 1:停用")
    private String status;

    @ApiModelProperty(value = "负责人", example = "张主任")
    private String master;

    @ApiModelProperty(value = "详细地址", example = "建国路88号")
    private String address;

    @ApiModelProperty(value = "地点类型ID", example = "1", notes = "关联字典表")
    private String locationTypeId;
}