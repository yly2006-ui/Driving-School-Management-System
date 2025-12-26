package com.mashang.mashangdriving.domain.param.manager.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DrivingBillRecordQuery {

    @ApiModelProperty(value = "用户名称", example = "张三")
    private String userName;
    @ApiModelProperty(value = "支付时间", example = "2024-01-15 10:30:00")
    private Date paymentTime;
    @ApiModelProperty(value = "用户角色", example = "学生", allowableValues = "学生,教练,管理员")
    private String roleName;
    @ApiModelProperty(value = "支付方式", example = "0")
    private String paymentMethod;
}
