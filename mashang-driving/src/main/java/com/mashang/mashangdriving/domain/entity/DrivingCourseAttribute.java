package com.mashang.mashangdriving.domain.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

@Data
public class DrivingCourseAttribute {

    @TableId(type = IdType.AUTO)
    private Long attributeId;
    private String attributeName;
    private String Introduction;
    private String delFlag;
    private String type;
}
