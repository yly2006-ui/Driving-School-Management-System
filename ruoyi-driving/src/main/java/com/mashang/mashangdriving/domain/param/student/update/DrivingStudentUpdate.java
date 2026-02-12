package com.mashang.mashangdriving.domain.param.student.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * 驾校学员实体类
 */
@Data
@ApiModel(description = "驾校学员信息")
public class DrivingStudentUpdate {

    @ApiModelProperty(value = "学员ID", example = "1")
    private Long studentId;

    @ApiModelProperty(value = "学员姓名", example = "张三")
    private String studentName;

    @ApiModelProperty(value = "手机号码",  example = "13800138000")
    private String phone;

    @ApiModelProperty(value = "头像",  example = "13800138000")
    private String avatar;

    @ApiParam(value = "头像文件", required = true)
    private String file;


}