package com.mashang.mashangdriving.domain.param.student.create;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingCourseAttributeCreate {

    private String attributeName;
    private String Introduction;
    private String type;
}
