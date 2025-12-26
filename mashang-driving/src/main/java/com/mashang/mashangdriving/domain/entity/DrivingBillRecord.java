package com.mashang.mashangdriving.domain.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

@Data
public class DrivingBillRecord {

    @TableId(type = IdType.AUTO)
    private Long recordId;
    private Long payId;
    private Long userId;
    private Long chargeLtemId;
    private Long roleId;
    private String delFlag;
}
