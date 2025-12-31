package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingRating {

    @TableId(type = IdType.AUTO)
    private Long ratingId;

    private double score;

    private String contanct;

    private Long instructorId;

    private Long studentId;;
}
