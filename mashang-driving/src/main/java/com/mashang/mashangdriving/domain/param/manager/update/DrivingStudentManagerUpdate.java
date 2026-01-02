package com.mashang.mashangdriving.domain.param.manager.update;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mashang.mashangdriving.domain.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DrivingStudentManagerUpdate {
    @ApiModelProperty(value = "学员ID",required = true, example = "1")
    private Long studentId;

    @ApiModelProperty(value = "学员姓名",example = "张三")
    private String studentName;

    @ApiModelProperty(value = "驾照类型ID", example = "1")
    private Long driverLicenseId;

    @ApiModelProperty(value = "手机号码",  example = "13800138000")
    private String phone;

    @ApiModelProperty(value = "紧急联系人电话", example = "13900139000")
    private String emergencyPhone;

    @ApiModelProperty(value = "学员状态",  example = "0(再学)")
    private String status;

    @ApiModelProperty(value = "身份证号",  example = "110101199001011234")
    private String idNumber;

    @ApiModelProperty(value = "身份证正面照片URL", example = "/upload/id/front/xxx.jpg")
    private String idNumberFront;

    @ApiModelProperty(value = "身份证背面照片URL", example = "/upload/id/back/xxx.jpg")
    private String idNumberBack;

    @ApiModelProperty(value = "修改时间", example = "2024-01-15 10:30:00", hidden = true)
    private Date updateTime;

    @ApiModelProperty(value = "教练学员表")
    private DrivingInstructorStudent drivingInstructorStudent;
}
