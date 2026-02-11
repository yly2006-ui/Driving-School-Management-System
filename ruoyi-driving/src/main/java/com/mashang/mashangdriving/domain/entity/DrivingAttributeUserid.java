package com.mashang.mashangdriving.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DrivingAttributeUserid {

    @TableId(type = IdType.AUTO)
    private Long attributeUserId;
    private Long attributeId;
    private Long userId;
//    private String status;
    private String delFlag;
}
