package com.mashang.mashangdriving.domain.utils;

import com.mashang.mashangdriving.domain.entity.DrivingCoachTimeSchedule;
import com.mashang.mashangdriving.domain.vo.manager.TimeGridVO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoachTimeGridUtil {

    // 改用LocalDateTime专用的格式化器
    private static final DateTimeFormatter timeKeyFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

    /**
     * 生成指定年月的【4-20点】空白模板
     */
    public static List<TimeGridVO> generateCoachTemplate(Integer year, Integer month) {
        List<TimeGridVO> gridList = new ArrayList<>();
        // 修复12月的问题：用YearMonth处理月份
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 4, 0);
        // 获取当月最后一天
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusDays(1);
        // 结束时间是当月最后一天的20点
        LocalDateTime end = endOfMonth.withHour(20).withMinute(0);

        LocalDateTime current = startOfMonth;
        while (!current.isAfter(end)) {
            // 只生成4-20点的小时
            int hour = current.getHour();
            if (hour < 4 || hour > 20) {
                current = current.plusHours(1);
                continue;
            }

            TimeGridVO grid = new TimeGridVO();
            // 生成timeKey
            grid.setTimeKey(current.format(timeKeyFormatter));
            // 拆分字段
            grid.setYear(current.getYear());
            grid.setMonth(current.getMonthValue());
            grid.setDay(current.getDayOfMonth());
            grid.setHour(hour);
            grid.setStatus(0); // 默认未预约
            grid.setPerson("0");
            gridList.add(grid);

            current = current.plusHours(1);
        }
        return gridList;
    }

    /**
     * 填充数据库数据到模板
     */
    public static List<TimeGridVO> fillCoachData(List<TimeGridVO> templateList, List<DrivingCoachTimeSchedule> scheduleList) {
        Map<String, DrivingCoachTimeSchedule> scheduleMap = new HashMap<>();
        for (DrivingCoachTimeSchedule schedule : scheduleList) {
            LocalDateTime startTime = schedule.getStartTime();
            if (startTime == null) {
                continue;
            }
            // 正确生成timeKey
            String timeKey = startTime.format(timeKeyFormatter);
            scheduleMap.put(timeKey, schedule);
        }

        for (TimeGridVO grid : templateList) {
            DrivingCoachTimeSchedule schedule = scheduleMap.get(grid.getTimeKey());
            if (schedule != null) {
                grid.setStatus(1); // 数据库有数据则标记为可预约
                grid.setPerson(schedule.getPerson());
            }
        }
        return templateList;
    }
}