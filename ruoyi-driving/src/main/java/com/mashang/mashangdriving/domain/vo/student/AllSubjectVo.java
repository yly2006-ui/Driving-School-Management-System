package com.mashang.mashangdriving.domain.vo.student;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AllSubjectVo {

    @ApiModelProperty("科目id")
  private Long subjectId;

    @ApiModelProperty("科目名称")
  private String subjectName;

    @ApiModelProperty("科目标签")
  private String tag;

}
