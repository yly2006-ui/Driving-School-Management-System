package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.DrivingCoachTimeSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DrivingCoachTimeScheduleMapper extends BaseMapper<DrivingCoachTimeSchedule> {
    /**
     * 按年月查询教练可预约时间（过滤del_flag=0）
     * @param year 年
     * @param month 月
     * @return 该月可预约时间列表
     */
    List<DrivingCoachTimeSchedule> selectCoachTimeByYearAndMonth(
            @Param("year") Integer year,
            @Param("month") Integer month,Long instructorId);
}
