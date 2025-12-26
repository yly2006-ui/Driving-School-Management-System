package com.mashang.mashangdriving.domain.vo.student;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 驾校学员实体类
 */
@Data
@ApiModel(description = "驾校学员信息")
public class DrivingStudentDtlVo {

    @ApiModelProperty(value = "学员ID", example = "1")
    private Long studentId;

    @ApiModelProperty(value = "学员姓名", example = "张三")
    private String studentName;

    @ApiModelProperty(value = "驾照类型编码", example = "C1")
    private String driverLicenseCode;

    @ApiModelProperty(value = "手机号码",  example = "13800138000")
    private String phone;

    @ApiModelProperty(value = "学员编号", example = "13800138000")
    private String studentCode;

    @ApiModelProperty(value = "头像",  example = "13800138000")
    private String avatar;

    @ApiModelProperty(value = "身份证号", example = "110101199001011234")
    private String idNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", example = "2024-01-15 10:30:00")
    private Date createTime;
}