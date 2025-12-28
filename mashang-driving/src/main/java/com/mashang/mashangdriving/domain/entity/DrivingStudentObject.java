package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingStudentObject {

    @TableId(type = IdType.AUTO)
    private Long studentId;

    private Long subjectId;

    private String delFlag;




}
