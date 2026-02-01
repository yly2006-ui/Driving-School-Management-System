package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingCoachTimeSchedule;

import java.util.List;

public interface IDrivingCoachTimeScheduleService extends IService<DrivingCoachTimeSchedule> {

    /**
     * 按年月查询教练可预约时间
     * @param year 年
     * @param month 月
     * @return 可预约时间列表
     */
    List<DrivingCoachTimeSchedule> selectCoachTimeByYearAndMonth(Integer year, Integer month);
}
