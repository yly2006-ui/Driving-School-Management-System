package com.mashang.mashangdriving.domain.param.student.create;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingLableCreate {

    private String lableName;
    private Long courseId;
    private Long attributeId;
}
