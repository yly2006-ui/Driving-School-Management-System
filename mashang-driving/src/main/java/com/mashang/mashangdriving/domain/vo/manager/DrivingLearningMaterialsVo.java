package com.mashang.mashangdriving.domain.vo.manager;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingLearningMaterialsVo {

    @TableId(type = IdType.AUTO)
    private Long materialsId;
    private String materialsName;
}
