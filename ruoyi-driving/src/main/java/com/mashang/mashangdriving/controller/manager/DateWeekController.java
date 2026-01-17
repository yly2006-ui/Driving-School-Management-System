package com.mashang.mashangdriving.controller.manager;


import com.mashang.mashangdriving.domain.vo.manager.DayWeekVO;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期-星期查询控制器
 * 核心功能：接收"2026年1月"格式字符串，返回该月每一天的日期+对应星期名称
 * 设计原则：字段极简（仅day+weekName）、无数组映射、参数校验完备
 */
@Api(tags = "管理端-个人信息时间")
@RestController
public class DateWeekController extends BaseController {

    // 1. 正则表达式：严格匹配「YYYY年M月」格式（4位年+1/2位月）
    private static final Pattern MONTH_DATE_PATTERN = Pattern.compile("^(\\d{4})年(\\d{1,2})月$");

    // 2. 枚举→中文星期名称映射（无需数组，语义化更清晰）
    private static final Map<DayOfWeek, String> WEEK_NAME_MAPPING;
    static {
        WEEK_NAME_MAPPING = new HashMap<>();
        WEEK_NAME_MAPPING.put(DayOfWeek.MONDAY, "周一");
        WEEK_NAME_MAPPING.put(DayOfWeek.TUESDAY, "周二");
        WEEK_NAME_MAPPING.put(DayOfWeek.WEDNESDAY, "周三");
        WEEK_NAME_MAPPING.put(DayOfWeek.THURSDAY, "周四");
        WEEK_NAME_MAPPING.put(DayOfWeek.FRIDAY, "周五");
        WEEK_NAME_MAPPING.put(DayOfWeek.SATURDAY, "周六");
        WEEK_NAME_MAPPING.put(DayOfWeek.SUNDAY, "周日");
    }

    /**
     * 查询指定月份每一天的日期和对应星期名称
     * @param dateStr 日期字符串，格式如：2026年1月、2026年12月
     * @return 通用响应结果，包含该月所有日期-星期名称列表
     */
    @ApiOperation("日期对应星期")
    @GetMapping("/api/date/month-week")
    public R<List<DayWeekVO>> getMonthDayWeek(@RequestParam String dateStr) {
        // ========== 第一步：参数非空校验 ==========
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return R.fail(400, "日期字符串不能为空，请传入如「2026年1月」的格式");
        }

        // ========== 第二步：正则解析年、月 ==========
        Matcher matcher = MONTH_DATE_PATTERN.matcher(dateStr.trim());
        if (!matcher.matches()) {
            return R.fail(400, "日期格式错误，请严格按照「YYYY年M月」格式传入（例：2026年1月）");
        }

        // ========== 第三步：转换年、月为数字并校验范围 ==========
        int year, month;
        try {
            year = Integer.parseInt(matcher.group(1)); // 提取4位年份
            month = Integer.parseInt(matcher.group(2)); // 提取1/2位月份
        } catch (NumberFormatException e) {
            return R.fail(400, "年份/月份必须为有效数字，请检查输入格式");
        }

        // 年份范围限制（可根据业务调整，比如1970-2100）
        if (year < 1970 || year > 2100) {
            return R.fail(400, "年份范围需在1970-2100之间，请调整后重试");
        }
        // 月份范围校验（1-12）
        if (month < 1 || month > 12) {
            return R.fail(400, "月份需在1-12之间，请检查输入");
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth(); // 自动处理平年/闰年/2月

        List<DayWeekVO> dayWeekList = new ArrayList<>();
        for (int day = 1; day <= daysInMonth; day++) {
            // 构造当天的LocalDateTime（时间部分设为00:00:00，仅关注日期）
            LocalDateTime currentDateTime = LocalDateTime.of(year, month, day, 0, 0);
            // 获取星期枚举（语义化，无需数字转换）
            DayOfWeek dayOfWeek = currentDateTime.getDayOfWeek();
            // 直接通过枚举映射获取中文星期名称
            String weekName = WEEK_NAME_MAPPING.get(dayOfWeek);

            // 封装VO（仅day+weekName，无冗余字段）
            DayWeekVO dayWeekVO = new DayWeekVO();
            dayWeekVO.setDay(day);
            dayWeekVO.setWeekName(weekName);
            dayWeekList.add(dayWeekVO);
        }

        // ========== 第六步：返回成功结果 ==========
        return R.ok(dayWeekList);
    }
}