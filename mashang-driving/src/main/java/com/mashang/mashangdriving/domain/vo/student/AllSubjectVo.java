package com.mashang.mashangdriving.domain.vo.student;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
