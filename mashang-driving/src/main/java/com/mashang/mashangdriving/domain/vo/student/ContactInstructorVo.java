package com.mashang.mashangdriving.domain.vo.student;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContactInstructorVo {
    @ApiModelProperty("教练id")
    private Long instructorId;

    @ApiModelProperty("教练名称")
    private String instructorName;

    @ApiModelProperty("教练联系方式")
    private String phone;
}
