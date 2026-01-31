package com.mashang.mashangdriving.domain.param.manager.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 驾校学员实体类
 */
@Data
@ApiModel(description = "查询学员信息")
public class DrivingStudentQuery {
    @ApiModelProperty(value = "学生姓名、身份证号或手机号")
    private String keyword;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "科目ID")
    private String subjectId;

}