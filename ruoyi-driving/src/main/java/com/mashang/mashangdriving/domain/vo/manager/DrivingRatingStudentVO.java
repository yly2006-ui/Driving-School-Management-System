package com.mashang.mashangdriving.domain.vo.manager;

import lombok.Data;

import java.util.Date;

@Data
public class DrivingRatingStudentVO {
    private Long ratingId;
    private Long subjectId;
    private String subjectName;

    private Long instructorId;
    private String instructorName;

    private Long studentId;
    private String studentName;
    private Long studentUserId;

    private String contanct;
    private Double score;
    private Date createTime;
}
