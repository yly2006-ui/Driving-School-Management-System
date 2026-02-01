package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingCoachTimeSchedule;
import com.mashang.mashangdriving.mapper.manager.DrivingCoachTimeScheduleMapper;
import com.mashang.mashangdriving.service.manager.IDrivingCoachTimeScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrivingCoachTimeScheduleServiceImpl extends ServiceImpl<DrivingCoachTimeScheduleMapper,
        DrivingCoachTimeSchedule> implements IDrivingCoachTimeScheduleService {
    @Autowired
    private DrivingCoachTimeScheduleMapper coachTimeScheduleMapper;

    @Override
    public List<DrivingCoachTimeSchedule> selectCoachTimeByYearAndMonth(Integer year, Integer month) {
        // 直接调Mapper，逻辑下放，符合若依规范
        return coachTimeScheduleMapper.selectCoachTimeByYearAndMonth(year, month);
    }

}
