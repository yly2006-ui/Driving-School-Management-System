package com.mashang.mashangdriving.domain.param.student.update;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingCourseAttributeUpdate {

    @TableId(type = IdType.AUTO)
    private Long attributeId;
    private String attributeName;
    private String Introduction;
    private String type;
}
