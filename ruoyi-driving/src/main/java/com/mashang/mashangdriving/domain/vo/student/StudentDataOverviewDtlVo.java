package com.mashang.mashangdriving.domain.vo.student;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("首页--学员端数据概览响应参数")
public class StudentDataOverviewDtlVo {

    @ApiModelProperty("学员id")
    private Long studentId;

    @ApiModelProperty("学员名称")
    private String studentName;

    @ApiModelProperty("学员当前学习科目id")
    private Long subjectId;

    @ApiModelProperty("学员当前学习科目名称")
    private String subjectName;

    @ApiModelProperty("学员头像")
    private String avatar;

    @ApiModelProperty("最新通知")
    private List<StudentDataOverviewNoticeDtlVo> DataOverviewNoticeDtlVoS;
}
