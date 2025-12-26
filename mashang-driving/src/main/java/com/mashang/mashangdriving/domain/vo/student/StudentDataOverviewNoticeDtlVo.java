package com.mashang.mashangdriving.domain.vo.student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("首页--数据概览响应参数(最新通知)")
public class StudentDataOverviewNoticeDtlVo {

    @ApiModelProperty("通知类型id")
    private Long typeId;

    @ApiModelProperty("通知类型名称")
    private String typeName;

    @ApiModelProperty("通知id")
    private Long noticeId;

    @ApiModelProperty("通知内容")
    private String text;

    @ApiModelProperty("通知时间")
    private Date publishTime;

    @ApiModelProperty("通知logo")
    private String titlePhoto;
}
