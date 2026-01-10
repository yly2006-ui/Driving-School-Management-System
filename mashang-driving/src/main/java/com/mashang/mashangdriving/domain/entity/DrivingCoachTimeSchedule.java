package com.mashang.mashangdriving.domain.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

@Data
public class DrivingCoachTimeSchedule {


    private String instructorId;
    @TableId(type = IdType.AUTO)
    private Long scheduleId;
    private Date startTime;
    private Date endTime;
    private String status;
    private String delFlag;
}
