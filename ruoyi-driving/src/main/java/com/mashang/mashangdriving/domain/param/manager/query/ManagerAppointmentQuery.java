package com.mashang.mashangdriving.domain.param.manager.query;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("管理管预约分页查询参数")
public class ManagerAppointmentQuery {

    @ApiModelProperty(value = "预约状态")
    private String status;

    @ApiModelProperty(value = "学员姓名")
    private String studentName;

    @ApiModelProperty(value = "学员手机号码")
    private String phone;

    @ApiModelProperty(value = "教练姓名")
    private String instructorName;

    @ApiModelProperty(value = "科目名称")
    private String subjectName;

}
