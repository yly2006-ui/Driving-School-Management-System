package com.mashang.mashangdriving.domain.vo.student;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DrivingCourseAndContentVo {
    @ApiModelProperty(value = "内容ID", example = "1001")
    private Integer contentId;

    @ApiModelProperty(value = "内容名称", example = "科目一考试视频")
    private String contectName; // 注意：原字段拼写错误

    @ApiModelProperty(value = "内容时长（秒）", example = "3600")
    private Long contentTime;

    @ApiModelProperty(value = "课程内容描述", example = "本节讲解交通规则...")
    private String courseContect; // 注意：原字段拼写错误

    @ApiModelProperty(value = "教学视频路径", example = "/video/lesson1.mp4")
    private String ipstructionalVideo;

    @ApiModelProperty(value = "教学视频解释")
    private String videoExplain;

    @ApiModelProperty("上一节id")
    private Long prevContentId;

    @ApiModelProperty("下一节id")
    private Long nextContentId;

}
