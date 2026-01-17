package com.mashang.mashangdriving.domain.vo.student;

import lombok.Data;
//给 AI 用的统计 VO（不返回前端）
@Data
public class TimeSlotStatVO {
    /**
     * 09:00-11:00
     */
    private String timeSlot;

    /**
     * 该时间段预约数量
     */
    private Integer count;
}
