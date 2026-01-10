package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.mashang.mashangdriving.domain.entity.DrivingCoachTimeSchedule;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingCoachTimeScheduleCreate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCoachTimeScheduleVo;
import com.mashang.mashangdriving.mapping.manager.DrivingCoachTimeScheduleMapping;
import com.mashang.mashangdriving.service.manager.IDrivingCoachTimeScheduleService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(tags = "管理端-个人中心")
@RestController
@RequestMapping("/drivingCoachTimeSchedule")
public class DrivingCoachTimeScheduleController extends BaseController {

    private static final Pattern YEAR_MONTH = Pattern.compile("^(\\d{4})年(\\d{1,2})月$");
    @Autowired
    private IDrivingCoachTimeScheduleService drivingCoachTimeScheduleService;


    @GetMapping("/list")
    @ApiOperation("查询教练时间安排")
    public R<List<DrivingCoachTimeScheduleVo>> CoachTimeScheduleSelect(@RequestParam String yearAndMonth) {
        if (yearAndMonth == null || yearAndMonth.trim().isEmpty()) {
            return R.fail(400, "日期字符串不能为空，请传入如「2026年1月」的格式");
        }

        Matcher matcher = YEAR_MONTH.matcher(yearAndMonth.trim());
        if (!matcher.matches()) {
            return R.fail(400, "日期格式错误，请严格按照「YYYY年M月」格式传入（例：2026年1月）");
        }

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

        // 1. 获取指定年月的YearMonth对象
        YearMonth yearMonth = YearMonth.of(year, month);

        // 2. 拼接当月开始时间：年月的第一天 00:00:00
        LocalDateTime monthStartTime = LocalDateTime.of(year, month, 1, // 年、月、日（固定为1）
                0, 0, 0         // 时、分、秒（固定为00:00:00）
        );

        // 3. 拼接当月结束时间：年月的最后一天 23:59:59
        int lastDayOfMonth = yearMonth.lengthOfMonth(); // 获取当月最后一天（自动适配平/闰年）
        LocalDateTime monthEndTime = LocalDateTime.of(year, month, lastDayOfMonth, // 年、月、当月最后一天
                23, 59, 59                   // 时、分、秒（固定为23:59:59）
        );


        LambdaQueryWrapper<DrivingCoachTimeSchedule> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.ge(DrivingCoachTimeSchedule::getStartTime, monthStartTime);
        lambdaQueryWrapper.le(DrivingCoachTimeSchedule::getEndTime, monthEndTime);

        List<DrivingCoachTimeSchedule> list = drivingCoachTimeScheduleService.list(lambdaQueryWrapper);
        List<DrivingCoachTimeScheduleVo> listVo = DrivingCoachTimeScheduleMapping.INSTANCE.toListVo(list);
        return list != null && !list.isEmpty() ? R.ok(listVo) : R.fail("未查到对应时间安排");
    }

    @PostMapping("/batchAdd")
    @ApiOperation("批量新增教练时间安排")
    public R batchAddSchedule(@RequestBody List<DrivingCoachTimeScheduleCreate> scheduleList) {

        List<DrivingCoachTimeSchedule> create = DrivingCoachTimeScheduleMapping.INSTANCE.toCreate(scheduleList);
        for (DrivingCoachTimeScheduleCreate drivingCoachTimeScheduleCreate : scheduleList) {
            Date startTime = drivingCoachTimeScheduleCreate.getStartTime();
            Date endTime = drivingCoachTimeScheduleCreate.getEndTime();
            LambdaQueryWrapper<DrivingCoachTimeSchedule> lambdaQueryWrapper =
                    new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DrivingCoachTimeSchedule::getStartTime, startTime);
            lambdaQueryWrapper.eq(DrivingCoachTimeSchedule::getEndTime, endTime);
            lambdaQueryWrapper.eq(DrivingCoachTimeSchedule::getInstructorId, drivingCoachTimeScheduleCreate.getInstructorId());
            lambdaQueryWrapper.eq(DrivingCoachTimeSchedule::getDelFlag,0);
            DrivingCoachTimeSchedule one = drivingCoachTimeScheduleService.getOne(lambdaQueryWrapper);
            if (one != null) {
                return R.fail("教练Id为：" + one.getInstructorId() + "的教练已设置"+one.getStartTime()+"开始至"
                        +one.getEndTime()+"为可预约时段");
            }

        }
        boolean success = drivingCoachTimeScheduleService.saveBatch(create);
        return success ? R.ok("批量新增成功") : R.fail("批量新增失败");
    }

    @DeleteMapping("/delete/{scheduleId}")
    @ApiOperation("取消可预约时间安排")
    public R deleteById(@PathVariable Long scheduleId) {
        boolean b = drivingCoachTimeScheduleService.removeById(scheduleId);
        return toR(b);
    }
}