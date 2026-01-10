package com.mashang.mashangdriving.domain.param.manager.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class DrivingCoachTimeScheduleCreate {

    private String instructorId;
    private Date startTime;
    private Date endTime;
    private String status;
}
