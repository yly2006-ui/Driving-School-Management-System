package com.mashang.mashangdriving.domain.param.manager.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 驾校学员实体类
 */
@Data
@ApiModel(description = "查询学员信息")
public class DrivingStudentQuery {


    @ApiModelProperty(value = "学员姓名", example = "张三",required = true)
    private String studentName;

    @ApiModelProperty(value = "手机号码", example = "13800138000")
    private String phone;

    @ApiModelProperty(value = "身份证号", example = "110101199001011234")
    private String idNumber;
}