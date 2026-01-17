package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 地点管理实体类
 */
@Data
@ApiModel(description = "地点管理实体")
public class DrivingLocation {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "地点ID", example = "1")
    private Long locationId;

    @ApiModelProperty(value = "地点名称", example = "主训练场A", required = true)
    private String locationName;

    @ApiModelProperty(value = "地点类型ID", example = "1", notes = "关联字典表")
    private String locationTypeId;

    @ApiModelProperty(value = "地点编号", example = "LOC001")
    private String locationNo;

    @ApiModelProperty(value = "容量", example = "50人")
    private String capacity;

    @ApiModelProperty(value = "状态", example = "0", notes = "0:正常 1:停用")
    private String status;

    @ApiModelProperty(value = "删除标志", example = "0", notes = "0:正常 1:删除")
    private String delFlag;

    @ApiModelProperty(value = "负责人", example = "张主任")
    private String master;

    @ApiModelProperty(value = "地区ID", example = "110000", notes = "关联地区表")
    private Long areaId;

    @ApiModelProperty(value = "联系电话", example = "13800138000")
    private Long phoneNum;

    @ApiModelProperty(value = "备注", example = "主要训练场地")
    private String remark;

    @ApiModelProperty(value = "详细地址", example = "建国路88号")
    private String address;
}