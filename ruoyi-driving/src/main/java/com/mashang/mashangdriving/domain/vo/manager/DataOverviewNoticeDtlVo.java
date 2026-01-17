package com.mashang.mashangdriving.domain.vo.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("首页--数据概览响应参数(最新通知)")
public class DataOverviewNoticeDtlVo {

    @ApiModelProperty("通知id")
    private long noticeId;

    @ApiModelProperty("通知标题")
    private String title;

    @ApiModelProperty("通知内容")
    private String text;

    @ApiModelProperty("通知时间")
    private Date publishTime;

    @ApiModelProperty("通知logo")
    private String titlePhoto;
}
