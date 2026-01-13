package com.mashang.mashangdriving.domain.vo.student;

import lombok.Data;

@Data
public class AiInstructorRecommendVO {

    private Long instructorId;
    private String instructorName; // 教练名
    private String date;            // 日期：2024-12-22
    private String timeSlot;        // 时间段：09:00-11:00
    private Double score;           // 评分
    private String goodSubject;     // 擅长科目
}
