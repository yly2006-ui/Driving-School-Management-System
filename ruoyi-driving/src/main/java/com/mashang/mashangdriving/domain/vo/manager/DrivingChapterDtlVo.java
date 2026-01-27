package com.mashang.mashangdriving.domain.vo.manager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class DrivingChapterDtlVo {
    @ApiModelProperty(value = "章节ID", example = "1")
    private Integer chapterId;

    @ApiModelProperty(value = "章节名称", example = "第一章 道路交通安全")
    private String chapterName;

    private List<DrivingSectionDtlVo>drivingSectionDtlVos;
}
