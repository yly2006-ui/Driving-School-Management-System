package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingLable {

    @TableId(type = IdType.AUTO)
    private Long lableId;
    private String lableName;
    private String delFlag;
    private Long courseId;
    private Long attributeId;
}
