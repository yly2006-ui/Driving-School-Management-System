package com.mashang.mashangdriving.domain.vo.manager;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 地点管理实体类
 */
@Data
@ApiModel(description = "地点管理实体")
public class DrivingLocationListVo {
    @ApiModelProperty(value = "地点ID", example = "1")
    private Long locationId;

    @ApiModelProperty(value = "地点名称", example = "主训练场A")
    private String locationName;

    @ApiModelProperty(value = "地点类型ID", example = "1", notes = "关联字典表")
    private String locationTypeId;
    @ApiModelProperty(value = "地点类型名称",example = "体育场")
    private String locationTypeName;    // 转换后的名称

    @ApiModelProperty(value = "地点编号", example = "LOC001")
    private String locationNo;

    @ApiModelProperty(value = "容量", example = "50人")
    private String capacity;

    @ApiModelProperty(value = "状态", example = "0", notes = "0:正常 1:停用")
    private String status;
    @ApiModelProperty(value = "状态名称", example = "正常")
    private String statusName;



    @ApiModelProperty(value = "负责人", example = "张主任")
    private String master;


    @ApiModelProperty(value = "联系电话", example = "13800138000")
    private Long phoneNum;

    @ApiModelProperty(value = "备注", example = "主要训练场地")
    private String remark;

    @ApiModelProperty(value = "详细地址", example = "建国路88号")
    private String address;

    @TableField(exist = false)
    @ApiModelProperty(value = "省份名称")
    private String province;
    @TableField(exist = false)
    @ApiModelProperty(value = "市名称")
    private String city;
    @TableField(exist = false)
    @ApiModelProperty(value = "区名称")
    private String county;
    @TableField(exist = false)
    @ApiModelProperty(value = "定位")
    private String area=province+city+county;
}