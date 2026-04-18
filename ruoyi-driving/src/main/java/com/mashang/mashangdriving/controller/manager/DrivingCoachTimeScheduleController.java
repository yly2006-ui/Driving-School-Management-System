package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mashang.mashangdriving.domain.entity.DrivingCoachTimeSchedule;
import com.mashang.mashangdriving.domain.entity.DrivingInstructor;
import com.mashang.mashangdriving.domain.param.manager.delete.DrivingCoachTimeScheduleDelete;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingCoachTimeScheduleCreate;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingCoachTimeScheduleCreateAndInstructQuery;
import com.mashang.mashangdriving.domain.utils.CoachTimeGridUtil;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCoahTimeAndInstructorDtlVo;
import com.mashang.mashangdriving.domain.vo.manager.ProfileInfoVO;
import com.mashang.mashangdriving.domain.vo.manager.TimeGridVO;
import com.mashang.mashangdriving.mapping.manager.DrivingCoachTimeScheduleMapping;
import com.mashang.mashangdriving.service.manager.IDrivingCoachTimeScheduleService;
import com.mashang.mashangdriving.service.manager.IDrivingInstructorService;
import com.mashang.mashangdriving.service.manager.IMySysUserService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Validated
@Api(tags = "管理端-个人中心")
@RestController
@RequestMapping("/drivingCoachTimeSchedule")
public class DrivingCoachTimeScheduleController extends BaseController {

    private static final Pattern YEAR_MONTH = Pattern.compile("^(\\d{4})\\s*-\\s*(\\d{1,2})$");

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 改用LocalDateTime专用的格式化器
    private static final DateTimeFormatter timeKeyFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

    @Autowired
    private IDrivingCoachTimeScheduleService drivingCoachTimeScheduleService;
    @Autowired
    private IDrivingInstructorService drivingInstructorService;

    @Autowired
    private IMySysUserService mySysUserService;

    @GetMapping("selectUser")
    @ApiOperation("查询管理员信息详情")
    public R selectByUserId(){
        Long userId = SecurityUtils.getUserId();
        ProfileInfoVO profileInfoVO = mySysUserService.selectByuserId(userId);
        return profileInfoVO != null ? R.ok(profileInfoVO) : R.fail();
    }


//    @GetMapping("/list")
//    @ApiOperation("查询教练时间安排")
//    public R<List<DrivingCoachTimeScheduleVo>> CoachTimeScheduleSelect(@RequestParam String yearAndMonth) {
//        if (yearAndMonth == null || yearAndMonth.trim().isEmpty()) {
//            return R.fail(400, "日期字符串不能为空，请传入如「2026年1月」的格式");
//        }
//
//        Matcher matcher = YEAR_MONTH.matcher(yearAndMonth.trim());
//        if (!matcher.matches()) {
//            return R.fail(400, "日期格式错误，请严格按照「YYYY年M月」格式传入（例：2026年1月）");
//        }
//
//        int year, month;
//        try {
//            year = Integer.parseInt(matcher.group(1)); // 提取4位年份
//            month = Integer.parseInt(matcher.group(2)); // 提取1/2位月份
//        } catch (NumberFormatException e) {
//            return R.fail(400, "年份/月份必须为有效数字，请检查输入格式");
//        }
//
//        // 年份范围限制（可根据业务调整，比如1970-2100）
//        if (year < 1970 || year > 2100) {
//            return R.fail(400, "年份范围需在1970-2100之间，请调整后重试");
//        }
//        // 月份范围校验（1-12）
//        if (month < 1 || month > 12) {
//            return R.fail(400, "月份需在1-12之间，请检查输入");
//        }
//
//        // 1. 获取指定年月的YearMonth对象
//        YearMonth yearMonth = YearMonth.of(year, month);
//
//        // 2. 获取当月开始时间：年月的第一天 00:00:00
//        LocalDateTime monthStartTime = yearMonth.atDay(1).atStartOfDay();
//
//        // 3. 获取当月结束时间：年月的最后一天 23:59:59
//        LocalDateTime monthEndTime = yearMonth.atEndOfMonth().plusDays(1).atStartOfDay().minusSeconds(1);
//
//
//        LambdaQueryWrapper<DrivingCoachTimeSchedule> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.ge(DrivingCoachTimeSchedule::getStartTime, monthStartTime);
//        lambdaQueryWrapper.le(DrivingCoachTimeSchedule::getEndTime, monthEndTime);
//
//        List<DrivingCoachTimeSchedule> list = drivingCoachTimeScheduleService.list(lambdaQueryWrapper);
//        List<DrivingCoachTimeScheduleVo> listVo = DrivingCoachTimeScheduleMapping.INSTANCE.toListVo(list);
//        return list != null && !list.isEmpty() ? R.ok(listVo) : R.fail("未查到对应时间安排");
//    }

    @PostMapping("/batchAdd")
    @ApiOperation("批量新增教练时间安排")
    public R batchAddSchedule(@RequestBody  @Valid List<DrivingCoachTimeScheduleCreate> scheduleList) {

        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<DrivingInstructor>lqw=new LambdaQueryWrapper<>();
        lqw.eq(DrivingInstructor::getUserId,userId);
        DrivingInstructor instructor = drivingInstructorService.getOne(lqw);
        Long instructorId = instructor.getInstructorId();

//        for (DrivingCoachTimeScheduleCreate drivingCoachTimeScheduleCreate : scheduleList) {
//            LocalDateTime startTime = drivingCoachTimeScheduleCreate.getStartTime();
//            LocalDateTime endTime = drivingCoachTimeScheduleCreate.getEndTime();
//
//            LambdaQueryWrapper<DrivingCoachTimeSchedule> lambdaQueryWrapper =
//                    new LambdaQueryWrapper<>();
//            lambdaQueryWrapper.eq(DrivingCoachTimeSchedule::getStartTime, startTime);
//            lambdaQueryWrapper.eq(DrivingCoachTimeSchedule::getEndTime, endTime);
//            lambdaQueryWrapper.eq(DrivingCoachTimeSchedule::getUserId, userId);
//            lambdaQueryWrapper.eq(DrivingCoachTimeSchedule::getDelFlag,0);
//            DrivingCoachTimeSchedule one = drivingCoachTimeScheduleService.getOne(lambdaQueryWrapper);
//            if (one != null) {
//                return R.fail("教练Id为：" + one.getInstructorId() + "的教练已设置"
//                        + DATE_TIME_FORMATTER.format(one.getStartTime()) + "开始至"
//                        + DATE_TIME_FORMATTER.format(one.getEndTime()) + "为可预约时段");
//            }
//        }
        List<LocalDateTime> startTime = scheduleList.stream().map(DrivingCoachTimeScheduleCreate::getStartTime)
                .toList();
        List<LocalDateTime> endTime = scheduleList.stream().map(DrivingCoachTimeScheduleCreate::getEndTime)
                .toList();
        LambdaQueryWrapper<DrivingCoachTimeSchedule> lambdaQueryWrapper =
                    new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(DrivingCoachTimeSchedule::getStartTime, startTime);
            lambdaQueryWrapper.in(DrivingCoachTimeSchedule::getEndTime, endTime);
            lambdaQueryWrapper.eq(DrivingCoachTimeSchedule::getUserId, userId);
            lambdaQueryWrapper.eq(DrivingCoachTimeSchedule::getDelFlag,0);
//            数据库中的信息
        List<DrivingCoachTimeSchedule> list = drivingCoachTimeScheduleService.list(lambdaQueryWrapper);
//        转map
        Map<String, DrivingCoachTimeSchedule> collect = list.stream()
                .collect(Collectors.toMap(e -> e.getStartTime().
                                format(DATE_TIME_FORMATTER) + e.getEndTime().format(DATE_TIME_FORMATTER),
                                        e -> e
                ));
//        对比一下
        List<String> E=new ArrayList<>();
        scheduleList.forEach(e->{
            DrivingCoachTimeSchedule drivingCoachTimeSchedule = collect.get(e.getStartTime().format(DATE_TIME_FORMATTER)
                    +e.getEndTime().format(DATE_TIME_FORMATTER));
            if (drivingCoachTimeSchedule!=null){
            E.add(e.getStartTime().format(DATE_TIME_FORMATTER)+"开始到"
                  +e.getEndTime().format(DATE_TIME_FORMATTER));
            }
        });
        if (!E.isEmpty()) {
            throw new RuntimeException("你的时间"+E+"已预约");
        }


        List<DrivingCoachTimeSchedule> create = DrivingCoachTimeScheduleMapping.INSTANCE.toCreate(scheduleList);


        //stream流方法
        create.forEach(c->{c.setInstructorId(String.valueOf(instructorId));
            c.setUserId(userId);
            c.setPerson("0");
            c.setStatus("1");});
        boolean success = drivingCoachTimeScheduleService.saveBatch(create);
        return success ? R.ok("批量新增成功") : R.fail("批量新增失败");
    }

    @DeleteMapping("/delete")
    @ApiOperation("批量取消可预约时间安排")
    public R deleteById( @RequestBody @Validated List<DrivingCoachTimeScheduleDelete> deleteList) {
        if (deleteList == null || deleteList.isEmpty()) {
            return R.fail("待取消的时间安排列表不能为空");
        }
        for (DrivingCoachTimeScheduleDelete delete : deleteList) {
            if (delete.getStartTime() == null || delete.getEndTime() == null) {
                throw new RuntimeException("时间不能为空");
            }
        }

        List<LocalDateTime> endTime = deleteList.stream()
                .map(DrivingCoachTimeScheduleDelete::getEndTime).toList();
        List<LocalDateTime> startTime = deleteList.stream().map(DrivingCoachTimeScheduleDelete::getStartTime).toList();
        LambdaQueryWrapper<DrivingCoachTimeSchedule> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(DrivingCoachTimeSchedule::getStartTime, startTime);
        lambdaQueryWrapper.in(DrivingCoachTimeSchedule::getEndTime,endTime);
        lambdaQueryWrapper.eq(DrivingCoachTimeSchedule::getUserId, SecurityUtils.getUserId());
        List<DrivingCoachTimeSchedule> list = drivingCoachTimeScheduleService.list(lambdaQueryWrapper);
//        数据库中的数据集合转map
        Map<String, DrivingCoachTimeSchedule> scheduleMap=list.stream()
                .filter(schedule -> schedule.getStartTime() != null&&schedule.getEndTime()!=null)
                .collect(Collectors.toMap(
                        schedule->schedule.getStartTime().format(DATE_TIME_FORMATTER)+schedule.getEndTime()
                                .format(DATE_TIME_FORMATTER),
                        schedule->schedule));
//        前端传的转换为集合对比数据库中的数据
        deleteList.forEach(one->{
            DrivingCoachTimeSchedule drivingCoachTimeSchedule = scheduleMap.get(one.getStartTime().format(DATE_TIME_FORMATTER) + one.getEndTime()
                    .format(DATE_TIME_FORMATTER));
            if (drivingCoachTimeSchedule==null){
                throw new RuntimeException("不存在userid为"+SecurityUtils.getUserId()+"的"+one.getStartTime()
                        .format(DATE_TIME_FORMATTER)+"到" +one.getEndTime().format(DATE_TIME_FORMATTER)+"时间安排");
            }
        });


        List<DrivingCoachTimeSchedule> delete = DrivingCoachTimeScheduleMapping.INSTANCE.toDelete(deleteList);
        // 1. 先拿到当前用户 ID
        Long userId = SecurityUtils.getUserId();

        // 2. 用 Stream 把所有 startTime 抽出来
        List<LocalDateTime> startTimes = delete.stream()
                .map(DrivingCoachTimeSchedule::getStartTime)
                .collect(Collectors.toList());

        // 3. 用 Stream 把所有 endTime 抽出来
        List<LocalDateTime> endTimes = delete.stream()
                .map(DrivingCoachTimeSchedule::getEndTime)
                .collect(Collectors.toList());

        // 4. 构建批量删除条件（一次构造，一次删除）
        LambdaQueryWrapper<DrivingCoachTimeSchedule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DrivingCoachTimeSchedule::getUserId, userId);  // 固定用户
        wrapper.in(DrivingCoachTimeSchedule::getStartTime, startTimes); // 所有开始时间
        wrapper.in(DrivingCoachTimeSchedule::getEndTime, endTimes);     // 所有结束时间

        // 5. 一次性批量删除！！！
        boolean success = drivingCoachTimeScheduleService.remove(wrapper);

        return success?R.ok():R.fail();
    }

    @GetMapping("/selectByUserId")
    @ApiOperation("根据安排表查询教练信息")
    public  R selectByUserId( @Validated DrivingCoachTimeScheduleCreateAndInstructQuery coachTimeScheduleCreateAndInstructQuery) {
        LocalDateTime startTime = coachTimeScheduleCreateAndInstructQuery.getStartTime();
        LocalDateTime endTime = coachTimeScheduleCreateAndInstructQuery.getEndTime();

        LambdaQueryWrapper<DrivingCoachTimeSchedule> lambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(Collections.singleton
                        (coachTimeScheduleCreateAndInstructQuery.getStartTime())),
                DrivingCoachTimeSchedule::getStartTime, startTime);
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(Collections.singleton(coachTimeScheduleCreateAndInstructQuery.getEndTime())),
                DrivingCoachTimeSchedule::getEndTime, endTime);
        lambdaQueryWrapper.eq(DrivingCoachTimeSchedule::getUserId, SecurityUtils.getUserId());

        DrivingCoachTimeSchedule one = drivingCoachTimeScheduleService.getOne(lambdaQueryWrapper);
        String person = null;
        if (one != null) {
            person = one.getPerson();
        }
        LambdaQueryWrapper<DrivingInstructor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DrivingInstructor::getUserId, SecurityUtils.getUserId());
        DrivingInstructor drivingInstructor = drivingInstructorService.getOne(lqw);
        DrivingCoahTimeAndInstructorDtlVo DtlVo = DrivingCoachTimeScheduleMapping.INSTANCE.dtlVo(drivingInstructor);
        DtlVo.setPerson(person);
        return R.ok(DtlVo);

    }



    @ApiOperation("新版查询时间安排")
    @GetMapping("/month")
    public R<List<TimeGridVO>> getCoachMonthTimeGrid(
            @RequestParam @ApiParam(value = "标准年月格式，2026-10）",
                    required = true)String yearAndMonth) {


        // 1. 非空校验：先拦截空参
        if (!StringUtils.hasText(yearAndMonth)) {
            return R.fail(400, "年月参数不能为空，请传入如2026-1或2026-01的格式");
        }

        // 2. 先判断matches()为true才能取分组
        Matcher matcher = YEAR_MONTH.matcher(yearAndMonth.trim());

        if (!matcher.matches()) {
            return R.fail(400, "年月格式错误，请传入如2026-1或2026-01的格式");
        }

        int year, month;
        try {
             year = Integer.parseInt(matcher.group(1)); // 提取年份
             month = Integer.parseInt(matcher.group(2));// 提取月份
        } catch (NumberFormatException e) {
            return R.fail(400, "年份/月份必须为有效数字，请检查输入格式");
        }
        // 4. 年、月范围合法性校验
        if (year < 1970 || year > 2100) {
            return R.fail(400, "年份范围需在1970-2100之间，请调整后重试");
        }
        if (month < 1 || month > 12) {
            return R.fail(400, "月份需在1-12之间，请检查输入");
        }

        // 1. 生成4-20点空白模板
        List<TimeGridVO> template = CoachTimeGridUtil.generateCoachTemplate(year, month);
        // 2. 查你的数据库数据
        List<DrivingCoachTimeSchedule> scheduleList = drivingCoachTimeScheduleService.selectCoachTimeByYearAndMonth(year, month);

        /*
         最终优化版用stream流
         */
        // 1. 把数据库时间 → 转成【字符串】做 key
        Map<String, DrivingCoachTimeSchedule> scheduleMap = scheduleList.stream()
                // 必须过滤判空！
                .filter(schedule -> schedule.getStartTime() != null)
                .collect(Collectors.toMap(
                        schedule -> schedule.getStartTime().format(DATE_TIME_FORMATTER),
                        schedule -> schedule,
                        (oldVal, newVal) -> oldVal
                ));

        // 2. 对比模板数据
        template.forEach(grid -> {

            DrivingCoachTimeSchedule schedule = scheduleMap.get(grid.getTimeKey());

            if (schedule != null) {
                grid.setStatus(1);
                grid.setPerson(schedule.getPerson());
            }
        });





        return R.ok(template);
    }








}