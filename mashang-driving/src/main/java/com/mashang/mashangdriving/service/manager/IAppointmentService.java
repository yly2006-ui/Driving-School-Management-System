package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingAppointment;

public interface IAppointmentService extends IService<DrivingAppointment> {

    //昨日未处理预约
    int countYesterdayStatusOne();

    //今日未处理所有预约
    int countOnDayStatusOne();
}
