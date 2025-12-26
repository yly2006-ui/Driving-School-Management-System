package com.mashang.mashangdriving.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 驾校学员实体类
 */
@Data
@ApiModel(description = "驾校学员信息")
public class DrivingStudent {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "学员ID", example = "1")
    private Long studentId;

    @ApiModelProperty(value = "学员姓名", example = "张三")
    private String studentName;

    @ApiModelProperty(value = "关联系统用户ID", example = "1001")
    private Long userId;

    @ApiModelProperty(value = "当前科目ID", example = "2")
    private Long subjectId;

    @ApiModelProperty(value = "驾照类型ID", example = "1")
    private Long driverLicenseId;

    @ApiModelProperty(value = "手机号码", required = true, example = "13800138000")
    private String phone;

    @ApiModelProperty(value = "紧急联系人电话", example = "13900139000")
    private String emergencyPhone;

    @ApiModelProperty(value = "学员状态",  example = "0(再学)")
    private String status;

    @ApiModelProperty(value = "身份证号", required = true, example = "110101199001011234")
    private String idNumber;

    @ApiModelProperty(value = "身份证正面照片URL", example = "/upload/id/front/xxx.jpg")
    private String idNumberFront;

    @ApiModelProperty(value = "身份证背面照片URL", example = "/upload/id/back/xxx.jpg")
    private String idNumberBack;

    @ApiModelProperty(value = "删除标志(0-未删除 2-已删除)", example = "0")
    private String delFlag;

    @ApiModelProperty(value = "创建时间", example = "2024-01-15 10:30:00")
    private Date createTime;
    @ApiModelProperty(value = "修改时间", example = "2024-01-15 10:30:00")
    private Date updateTime;
}