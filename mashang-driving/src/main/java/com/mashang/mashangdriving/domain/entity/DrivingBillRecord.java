package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

@Data
public class DrivingBillRecord {

    @TableId(type = IdType.AUTO)
    private Long recordId;
    private Long payId;
    private Long userId;
    private Long chargeLtemId;
    private Long roleId;
    private String delFlag;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
