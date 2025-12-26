package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingAppointment;
import com.mashang.mashangdriving.mapper.manager.AppointmentMapper;
import com.mashang.mashangdriving.service.manager.IAppointmentService;
import com.ruoyi.common.constant.AppointmentConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, DrivingAppointment> implements IAppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public int countYesterdayStatusOne() {
        LocalDate today = LocalDate.now();

        // 昨天 00:00:00
        LocalDateTime startTime = today
                .minusDays(1)       // 回到昨天
                .atStartOfDay();    // 昨天 00:00:00

        // 今天 00:00:00
        LocalDateTime endTime = today.atStartOfDay();


        // ===== 2. 构造查询条件 =====

        LambdaQueryWrapper<DrivingAppointment> wrapper = Wrappers.lambdaQuery();

        // 条件 1：状态 = "1"
        wrapper.eq(DrivingAppointment::getStatus, AppointmentConstants.PROCESSED_STATUS);

        // 条件 2：创建时间 >= 昨天开始
        wrapper.ge(DrivingAppointment::getCreateTime, startTime);

        // 条件 3：创建时间 < 今天开始
        wrapper.lt(DrivingAppointment::getCreateTime, endTime);


        // ===== 3. 执行 count 查询 =====
        Long countLong = appointmentMapper.selectCount(wrapper);
        int countYesterdayStatusOne = (int) (countLong != null ? countLong.longValue() : 0L);
        return countYesterdayStatusOne;
    }

    @Override
    public int countOnDayStatusOne() {
        LambdaQueryWrapper<DrivingAppointment> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DrivingAppointment::getStatus, AppointmentConstants.PROCESSED_STATUS);
        Long countLong = appointmentMapper.selectCount(wrapper);
        int countOnDayStatusOne = (int) (countLong != null ? countLong.longValue() : 0L);
        return countOnDayStatusOne;
    }
}
