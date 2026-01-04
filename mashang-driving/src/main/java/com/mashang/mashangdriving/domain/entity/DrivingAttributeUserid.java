package com.mashang.mashangdriving.domain.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

@Data
public class DrivingAttributeUserid {

    @TableId(type = IdType.AUTO)
    private Long attributeId;
    private Long userId;
    private String status;
}
