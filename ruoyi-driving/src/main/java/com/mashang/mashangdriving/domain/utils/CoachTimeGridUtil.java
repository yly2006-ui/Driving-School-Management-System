package com.mashang.mashangdriving.domain.utils;


import com.mashang.mashangdriving.domain.entity.DrivingCoachTimeSchedule;
import com.mashang.mashangdriving.domain.vo.manager.TimeGridVO;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 适配你的数据库：教练时间格子工具类
 * 核心：生成4-20点模板 + 填充你的数据库数据（Date类型）
 * 无复杂语法，纯基础代码
 */
public class CoachTimeGridUtil {

    // 时间格式化器：转成和模板一致的timeKey（年-月-日 时）
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");

    /**
     * 生成指定年月的4-20点空白模板（和前端页面一致）
     * @param year 前端传的年（如2026）
     * @param month 前端传的月（如2，1-12）
     * @return 完整空白模板，默认status=0白色
     */
    public static List<TimeGridVO> generateCoachTemplate(Integer year, Integer month) {
        List<TimeGridVO> gridList = new ArrayList<>();
        // 1. 用java.time生成开始/结束时间（自动适配月份天数，不用管28/29/30/31）
        java.time.LocalDateTime start = java.time.LocalDateTime.of(year, month, 1, 4, 0);
        java.time.LocalDateTime end = java.time.LocalDateTime.of(year, month + 1, 1, 0, 0)
                .minusDays(1).withHour(20).withMinute(0);

        // 2. 循环生成每小时一个格子
        java.time.LocalDateTime current = start;
        while (!current.isAfter(end)) {
            TimeGridVO grid = new TimeGridVO();
            // 生成timeKey：yyyy-MM-dd HH（如2026-02-01 04）
            grid.setTimeKey(String.format("%d-%02d-%02d %02d",
                    current.getYear(), current.getMonthValue(),
                    current.getDayOfMonth(), current.getHour()));
            // 前端渲染字段：拆分年月日小时
            grid.setYear(current.getYear());
            grid.setMonth(current.getMonthValue());
            grid.setDay(current.getDayOfMonth());
            grid.setHour(current.getHour());
            gridList.add(grid);
            current = current.plusHours(1);
        }
        return gridList;
    }

    /**
     * 填充你的数据库数据到模板
     * @param templateList 空白模板
     * @param scheduleList 从你数据库查的教练时间数据
     * @return 填充后的数据，直接返回前端
     */
    public static List<TimeGridVO> fillCoachData(List<TimeGridVO> templateList, List<DrivingCoachTimeSchedule> scheduleList) {
        // 1. 数据库数据转Map：timeKey -> 数据，方便快速匹配
        Map<String, DrivingCoachTimeSchedule> scheduleMap = new HashMap<>();
        for (DrivingCoachTimeSchedule schedule : scheduleList) {
            // 把数据库的Date类型start_time转成timeKey（和模板一致）
            String timeKey = formatDateToTimeKey(schedule.getStartTime());
            if (!"".equals(timeKey)) {
                scheduleMap.put(timeKey, schedule);
            }
        }

        // 2. 循环模板匹配，自动转换status（数据库0→前端1）
        for (TimeGridVO grid : templateList) {
            DrivingCoachTimeSchedule schedule = scheduleMap.get(grid.getTimeKey());
            if (schedule != null) {
                grid.setStatus(1); // 数据库0=可预约→前端1绿色
                grid.setBizData(schedule.getPerson()); // 预约人放bizData，给前端展示
            }
        }
        return templateList;
    }

    /**
     * 辅助方法：Date转timeKey（yyyy-MM-dd HH），适配你的数据库
     * @param date 数据库的start_time（Date类型）
     * @return 标准timeKey
     */
    private static String formatDateToTimeKey(LocalDateTime date) {
        if (date == null) {
            return "";
        }
        try {
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }
}