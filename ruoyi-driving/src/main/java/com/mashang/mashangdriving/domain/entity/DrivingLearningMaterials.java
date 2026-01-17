package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingLearningMaterials {

    @TableId(type = IdType.AUTO)
    private Long materialsId;
    private String materialsName;
    private Long courseId;
    private String delFlag;
}
