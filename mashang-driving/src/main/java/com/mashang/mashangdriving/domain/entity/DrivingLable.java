package com.mashang.mashangdriving.domain.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

@Data
public class DrivingLable {

    @TableId(type = IdType.AUTO)
    private Long lableId;
    private String lableName;
    private String delFlag;
    private Long courseId;
    private Long attributeId;
}
