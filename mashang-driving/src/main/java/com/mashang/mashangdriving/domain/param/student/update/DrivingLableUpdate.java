package com.mashang.mashangdriving.domain.param.student.update;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingLableUpdate {

    private Long lableId;
    private String lableName;
    private Long courseId;
    private Long attributeId;
}
