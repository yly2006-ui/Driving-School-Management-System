package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.vo.student.StudentDataOverviewDtlVo;
import com.mashang.mashangdriving.mapper.student.StudentMapper;
import com.mashang.mashangdriving.service.student.IStudentService;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, DrivingStudent> implements
        IStudentService {

    @Autowired
    private StudentMapper studentMapper;


    @Override
    public int lastMonthStudentMount() {

        // 当前日期
        LocalDate now = LocalDate.now();

        // 上个月第一天
        LocalDateTime startTime = now
                .minusMonths(1)        // 回到上个月
                .withDayOfMonth(1)     // 设置为 1 号
                .atStartOfDay();       // 时间设为 00:00:00
        // 本月第一天
        LocalDateTime endTime = now
                .withDayOfMonth(1)     // 本月 1 号
                .atStartOfDay();       // 00:00:00

        // 使用 LambdaQueryWrapper，防止字段名写错
        LambdaQueryWrapper<DrivingStudent> wrapper = Wrappers.lambdaQuery();

        // create_time >= startTime
        wrapper.ge(DrivingStudent::getCreateTime, startTime);

        // create_time < endTime
        wrapper.lt(DrivingStudent::getCreateTime, endTime);

        Long countLong = studentMapper.selectCount(wrapper);
        int mountLastMonth = (int) (countLong != null ? countLong.longValue() : 0L);// 先判空，避免空指针

        return mountLastMonth;
    }

    @Override
    public int countLastMonthActiveStudent() {

        LocalDate now = LocalDate.now();

        // 上个月第一天 00:00:00
        LocalDateTime startTime = now
                .minusMonths(1)        // 回到上个月
                .withDayOfMonth(1)     // 设置为 1 号
                .atStartOfDay();       // 当天 00:00:00

        // 本月第一天 00:00:00（作为结束时间）
        LocalDateTime endTime = now
                .withDayOfMonth(1)
                .atStartOfDay();

        LambdaQueryWrapper<DrivingStudent> wrapper = Wrappers.lambdaQuery();

        // 条件 1：创建时间 >= 上个月开始
        wrapper.ge(DrivingStudent::getCreateTime, startTime);
        wrapper.lt(DrivingStudent::getCreateTime, endTime);

        // 条件 3：学生状态 = "1"
        wrapper.eq(DrivingStudent::getStatus, "1");


        // ===== 3. 执行 count 查询 =====

        // 数据库中执行：select count(*) from student where ...
        Long countLong = studentMapper.selectCount(wrapper);
        int mountLastMonth = (int) (countLong != null ? countLong.longValue() : 0L);

        return mountLastMonth;
    }

    @Override
    public int countOnMonthActiveStudent() {

        LambdaQueryWrapper<DrivingStudent> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DrivingStudent::getStatus, "1");

        Long countLong = studentMapper.selectCount(wrapper);
        int mountOnMonth = (int) (countLong != null ? countLong.longValue() : 0L);
        return mountOnMonth;
    }

    @Override
    public StudentDataOverviewDtlVo student() {

        Long userId = SecurityUtils.getLoginUser().getUserId();
        StudentDataOverviewDtlVo student = studentMapper.student(userId);
        return student;
    }
}
