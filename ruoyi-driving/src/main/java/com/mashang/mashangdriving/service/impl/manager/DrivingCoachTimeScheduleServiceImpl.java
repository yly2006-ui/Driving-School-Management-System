package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingCoachTimeSchedule;
import com.mashang.mashangdriving.domain.entity.DrivingInstructor;
import com.mashang.mashangdriving.mapper.manager.DrivingCoachTimeScheduleMapper;
import com.mashang.mashangdriving.mapper.manager.DrivingInstructorMapper;
import com.mashang.mashangdriving.service.manager.IDrivingCoachTimeScheduleService;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrivingCoachTimeScheduleServiceImpl extends ServiceImpl<DrivingCoachTimeScheduleMapper,
        DrivingCoachTimeSchedule> implements IDrivingCoachTimeScheduleService {
    @Autowired
    private DrivingCoachTimeScheduleMapper coachTimeScheduleMapper;

    @Autowired
    private DrivingInstructorMapper drivingInstructorMapper;

    @Override
    public List<DrivingCoachTimeSchedule> selectCoachTimeByYearAndMonth(Integer year, Integer month) {
        // 直接调Mapper，逻辑下放，符合若依规范
        LambdaQueryWrapper<DrivingInstructor>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DrivingInstructor::getUserId, SecurityUtils.getUserId());
        DrivingInstructor drivingInstructor = drivingInstructorMapper.selectOne(lambdaQueryWrapper);
        return coachTimeScheduleMapper.selectCoachTimeByYearAndMonth(year, month,drivingInstructor.getInstructorId());
    }

}
