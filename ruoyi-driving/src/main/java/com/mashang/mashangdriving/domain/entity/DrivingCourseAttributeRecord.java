package com.mashang.mashangdriving.domain.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

@Data
public class DrivingCourseAttributeRecord {

    @TableId(value = "study_id", type = IdType.AUTO)
    private Long studyId;
    private Long courseAttributeId;
    private Long userId;
}
