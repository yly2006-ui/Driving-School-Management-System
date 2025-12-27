package com.mashang.mashangdriving.domain.vo.manager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DrivingSectionDtlVo{
    @ApiModelProperty(value = "小节ID", example = "1")
    private Integer sectionId;

    @ApiModelProperty(value = "章ID", example = "1")
    private Integer chapterId;

    @ApiModelProperty(value = "小节名称", example = "道路交通安全法基本原则")
    private String sectionName;

    @ApiModelProperty(value = "小节解释", example = "道路交通安全法基本原则...")
    private String sectionExplain;

    @ApiModelProperty(value = "教学图片URL", example = "https://cn.bing.com/im...")
    private String picture;
}
