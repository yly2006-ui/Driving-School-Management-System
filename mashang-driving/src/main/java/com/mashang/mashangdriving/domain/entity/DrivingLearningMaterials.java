package com.mashang.mashangdriving.domain.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

@Data
public class DrivingLearningMaterials {

    @TableId(type = IdType.AUTO)
    private Long materialsId;
    private String materialsName;
    private Long courseId;
    private String delFlag;
}
