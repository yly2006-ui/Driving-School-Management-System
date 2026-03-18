package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;
import com.mashang.mashangdriving.domain.entity.DrivingPay;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingBillRecordQuery;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillMonthMessageVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillRecordListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillYearMessageVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingGroupMonthVo;
import com.mashang.mashangdriving.mapper.manager.DrivingBillRecordMapper;
import com.mashang.mashangdriving.mapper.student.PayMapper;
import com.mashang.mashangdriving.service.manager.IDrivingBillRecordService;
import com.ruoyi.common.utils.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DrivingBillRecordServiceImpl extends ServiceImpl<DrivingBillRecordMapper, DrivingBillRecord> implements IDrivingBillRecordService {
    @Autowired
    private DrivingBillRecordMapper drivingBillRecordMapper;
    @Autowired
    private PayMapper payMapper;

    public Page<DrivingBillRecordListVo> queryBillRecord(Page<DrivingBillRecordListVo> page,
                                                         @Param("query") DrivingBillRecordQuery query) {
        QueryWrapper<DrivingBillRecord> queryWrapper = new QueryWrapper<>();

        // 基础查询条件
        queryWrapper.like(StringUtils.isNotEmpty(query.getUserName()),
                "driving_student.student_name", query.getUserName());
        String roleId = query.getRoleId();
        if ("5".equals(roleId)) {
            queryWrapper.notIn("role.role_id",100,101,102,103);
        } else if(!"0".equals(roleId)) {
            queryWrapper.eq( "role.role_id", roleId);
        }
        queryWrapper.eq(StringUtils.isNotNull(query.getPaymentMethod()), "pay.pay_type", query.getPaymentMethod());
        queryWrapper.orderByDesc("r.create_time");
        queryWrapper.eq("r.del_flag", 0);

        // 时间条件处理
        handleTimeCondition(query, queryWrapper);

        // 执行查询
        Page<DrivingBillRecordListVo> queried = drivingBillRecordMapper.queryBillRecord(page, queryWrapper);

        // 金额格式处理
        if (queried != null && CollectionUtils.isNotEmpty(queried.getRecords())) {
            for (DrivingBillRecordListVo record : queried.getRecords()) {
                Long amount = record.getAmount();
                if (amount > 0) {
                    record.setFinalAmount("+" + amount);
                } else {
                    record.setFinalAmount("-" + amount);
                }
            }
        }
        return queried;
    }

    /**
     * 时间条件处理
     */
    private void handleTimeCondition(DrivingBillRecordQuery query, QueryWrapper<DrivingBillRecord> queryWrapper) {
        String endTime = query.getEndTime();
        String beginTime = query.getBeginTime();

        if (StringUtils.isNotEmpty(beginTime) && StringUtils.isNotEmpty(endTime)) {
            String startDate = parseDate(beginTime) + " 00:00:00";
            String endDate = parseDate(endTime) + " 23:59:59";
            queryWrapper.between("pay.create_time", startDate, endDate);
        } else if (StringUtils.isNotEmpty(endTime)) {
            String endDate = parseDate(endTime) + " 23:59:59";
            queryWrapper.le("pay.create_time", endDate);
        } else if (StringUtils.isNotEmpty(beginTime)) {
            String startDate = parseDate(beginTime) + " 00:00:00";
            queryWrapper.ge("pay.create_time", startDate);
        }
    }

    /**
     * 日期格式解析
     */
    private String parseDate(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return "";
        }

        try {
            String cleanDate = dateStr.replaceAll("[^0-9]", "");

            if (cleanDate.length() >= 8) {
                String year = cleanDate.substring(0, 4);
                String month = cleanDate.substring(4, 6);
                if (month.length() == 1) {
                    month = "0" + month;
                } else if (month.startsWith("0")) {
                    month = String.format("%02d", Integer.parseInt(month));
                }

                String day = cleanDate.substring(6, 8);
                if (day.length() == 1) {
                    day = "0" + day;
                } else if (day.startsWith("0")) {
                    day = String.format("%02d", Integer.parseInt(day));
                }

                return year + "-" + month + "-" + day;
            } else if (cleanDate.length() == 7) {
                String year = cleanDate.substring(0, 4);
                String monthPart = cleanDate.substring(4, 5);
                String dayPart = cleanDate.substring(5);

                String month = String.format("%02d", Integer.parseInt(monthPart));
                String day = String.format("%02d", Integer.parseInt(dayPart));

                return year + "-" + month + "-" + day;
            } else if (cleanDate.length() == 6) {
                if (cleanDate.startsWith("2")) {
                    String year = "20" + cleanDate.substring(0, 2);
                    String month = String.format("%02d", Integer.parseInt(cleanDate.substring(2, 4)));
                    String day = String.format("%02d", Integer.parseInt(cleanDate.substring(4)));
                    return year + "-" + month + "-" + day;
                }
            }
        } catch (Exception e) {
            System.out.println("日期解析错误: " + e.getMessage());
            return tryOtherDateFormat(dateStr);
        }

        return dateStr;
    }

    /**
     * 兼容其他日期格式
     */
    private String tryOtherDateFormat(String dateStr) {
        try {
            if (dateStr.contains("年") && dateStr.contains("月") && dateStr.contains("日")) {
                Pattern pattern = Pattern.compile("(\\d{4})年(\\d{1,2})月(\\d{1,2})日");
                Matcher matcher = pattern.matcher(dateStr);
                if (matcher.find()) {
                    String year = matcher.group(1);
                    String month = String.format("%02d", Integer.parseInt(matcher.group(2)));
                    String day = String.format("%02d", Integer.parseInt(matcher.group(3)));
                    return year + "-" + month + "-" + day;
                }
            } else if (dateStr.contains("-")) {
                String[] parts = dateStr.split("-");
                if (parts.length == 3) {
                    String year = parts[0];
                    String month = String.format("%02d", Integer.parseInt(parts[1]));
                    String day = String.format("%02d", Integer.parseInt(parts[2]));
                    return year + "-" + month + "-" + day;
                }
            } else if (dateStr.contains("/")) {
                String[] parts = dateStr.split("/");
                if (parts.length == 3) {
                    String year = parts[0];
                    String month = String.format("%02d", Integer.parseInt(parts[1]));
                    String day = String.format("%02d", Integer.parseInt(parts[2]));
                    return year + "-" + month + "-" + day;
                }
            }
        } catch (Exception e) {
            System.out.println("其他日期格式解析错误: " + e.getMessage());
        }

        System.out.println("无法解析的日期格式: " + dateStr);
        return dateStr;
    }

    // 年度财务汇总（核心方法：仅返回单个实体类）
    @Override
    public DrivingBillYearMessageVo queryAll(String year) {
        String yearNumber = year.replaceAll("[^0-9]", "");
        QueryWrapper<DrivingBillRecord> queryWrapper = new QueryWrapper<>();
        int time =Integer.parseInt(yearNumber);

        // 年收入查询
        queryWrapper.apply("YEAR(b.create_time) = {0}",time);
        queryWrapper.eq("b.role_id", 102);
        DrivingBillYearMessageVo AnnualTotalIncome = drivingBillRecordMapper.queryAnnualTotalIncome(queryWrapper);
        if (AnnualTotalIncome == null) {
            AnnualTotalIncome = new DrivingBillYearMessageVo();
            AnnualTotalIncome.setAnnualTotalIncome("0");
        }

        // 年支出查询
        QueryWrapper<DrivingBillRecord> wrapper = new QueryWrapper<>();
        wrapper.apply("YEAR(b.create_time) = {0}",time);
        wrapper.eq("b.role_id", 101);
        DrivingBillYearMessageVo AnnualTotalExpenditure = drivingBillRecordMapper.queryAnnualTotalExpenditure(wrapper);
        if (AnnualTotalExpenditure == null) {
            AnnualTotalExpenditure = new DrivingBillYearMessageVo();
            AnnualTotalExpenditure.setAnnualTotalExpenditure("0");
        }

        // 学生总数查询
        QueryWrapper<DrivingBillRecord> wrappered = new QueryWrapper<>();
        wrappered.apply("YEAR(s.create_time) = {0}",time);
        DrivingBillYearMessageVo allStudentCount = drivingBillRecordMapper.queryAllStudentCount(wrappered);
        if (allStudentCount == null) {
            allStudentCount = new DrivingBillYearMessageVo();
            allStudentCount.setTotalStudents("0");
        }

        DrivingBillYearMessageVo YearMessageVo = new DrivingBillYearMessageVo();

        String Expenditure = AnnualTotalExpenditure.getAnnualTotalExpenditure();
        String TotalIncome = AnnualTotalIncome.getAnnualTotalIncome();

        BigDecimal bd1 = new BigDecimal(Expenditure);
        BigDecimal bd2 = new BigDecimal(TotalIncome);
        BigDecimal NetProfit = bd2.subtract(bd1);

        String totalStudents = allStudentCount.getTotalStudents();
        System.out.println("今年一共存在的学生数量" + totalStudents);

        // 利润率计算
        BigDecimal profitMarginPercent = BigDecimal.ZERO;
        if (bd2.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal divide = NetProfit.divide(bd2, 3, RoundingMode.HALF_UP);
            profitMarginPercent = divide.multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP);
        }
        String yearNetProfit = profitMarginPercent + "%";

        // 去年收入查询
        int lastYear = Integer.parseInt(yearNumber) - 1;
        String start = lastYear + "-01-01 00:00:00";
        String end = lastYear + "-12-31 23:59:59";
        QueryWrapper<DrivingBillRecord> w = new QueryWrapper<>();
        w.between("b.create_time", start, end);
        w.eq("b.role_id", 102);
        DrivingBillYearMessageVo lastAnnualTotalIncome = drivingBillRecordMapper.queryAnnualTotalIncome(w);
        if (lastAnnualTotalIncome == null) {
            lastAnnualTotalIncome = new DrivingBillYearMessageVo();
            lastAnnualTotalIncome.setAnnualTotalIncome("0");
        }

        // 收入同比计算
        BigDecimal lastAnnual = new BigDecimal(lastAnnualTotalIncome.getAnnualTotalIncome());
        String string = "0%";
        if (lastAnnual.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal subtract = bd2.subtract(lastAnnual);
            BigDecimal a = subtract.divide(lastAnnual, 3, RoundingMode.HALF_UP);
            BigDecimal multiply = a.multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP);
            string = multiply + "%";
        }

        // 去年支出查询
        QueryWrapper<DrivingBillRecord> r = new QueryWrapper<>();
        r.between("b.create_time", start, end);
        r.eq("b.role_id", 101);
        DrivingBillYearMessageVo e = drivingBillRecordMapper.queryAnnualTotalExpenditure(r);
        if (e == null) {
            e = new DrivingBillYearMessageVo();
            e.setAnnualTotalExpenditure("0");
        }
        System.out.println("去年支出" + e.getAnnualTotalExpenditure());

        // 支出同比计算
        BigDecimal t = new BigDecimal(e.getAnnualTotalExpenditure());
        String n = "0%";
        if (t.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal i = bd1.subtract(t);
            BigDecimal p = i.divide(t, 3, RoundingMode.HALF_UP);
            BigDecimal l = p.multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP);
            n = l + "%";
        }

        // 净利润同比计算
        BigDecimal lastyear = lastAnnual.subtract(t);
        String s1 = "0%";
        if (lastyear.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal divide1 = NetProfit.subtract(lastyear).divide(lastyear, 3, RoundingMode.HALF_UP);
            BigDecimal scale1 = divide1.multiply(new BigDecimal(100)).setScale(1, RoundingMode.HALF_UP);
            s1 = scale1 + "%";
        }

        // 去年学生数查询
        QueryWrapper<DrivingBillRecord> student = new QueryWrapper<>();
        student.between("s.create_time", start, end);
        DrivingBillYearMessageVo studentTop = drivingBillRecordMapper.queryAllStudentCount(student);
        if (studentTop == null) {
            studentTop = new DrivingBillYearMessageVo();
            studentTop.setTotalStudents("0");
        }

        // 学生数同比计算
        BigDecimal studentBigDecimal = new BigDecimal(totalStudents);
        BigDecimal lastStudentBigDecimal = new BigDecimal(studentTop.getTotalStudents());
        String s = "0%";
        if (lastStudentBigDecimal.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal decimal = studentBigDecimal.subtract(lastStudentBigDecimal)
                    .divide(lastStudentBigDecimal, 3, RoundingMode.HALF_UP);
            BigDecimal multiply1 = decimal.multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP);
            s = multiply1 + "%";
        }

        String subtract1 = NetProfit.subtract(lastyear) + "%";

        // 完成培训学生数量查询
        QueryWrapper<DrivingBillRecord> finishedStudent = new QueryWrapper<>();
        finishedStudent.apply("YEAR(s.create_time) = {0}",time);
        finishedStudent.eq("s.status", "1");
        Long finisheded = drivingBillRecordMapper.finishedStudent(finishedStudent);
        if (finisheded == null) {
            finisheded = 0L;
        }
        System.out.println("完成培训学生数量" + finisheded);

        // 培训完成率计算
        BigDecimal bigDecimal = new BigDecimal(finisheded);
        String setScale = "0%";
        if (studentBigDecimal.compareTo(BigDecimal.ZERO) > 0) {
            setScale = bigDecimal.divide(studentBigDecimal, 3, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP) + "%";
        }

        // 结果赋值
        YearMessageVo.setAnnualTotalExpenditure(Expenditure);
        YearMessageVo.setAnnualTotalIncomelastYear(string);
        YearMessageVo.setAnnualTotalIncome(TotalIncome);
        YearMessageVo.setAnnualTotalExpenditurelastYear(n);

        YearMessageVo.setAnnualNetProfit(String.valueOf(NetProfit));
        YearMessageVo.setAnnualNetProfitlastYear(s1);

        YearMessageVo.setTotalStudents(totalStudents);
        YearMessageVo.setTotalStudentslastYear(s);
        YearMessageVo.setProfitMargin(yearNetProfit);
        YearMessageVo.setProfitMarginlastYear(subtract1);
        YearMessageVo.setCompletedTraining(bigDecimal.toString());
        YearMessageVo.setCompletedTrainingFinsh(setScale);

        // 直接返回单个实体类（无集合）
        return YearMessageVo;
    }

    // 移除多余的集合版方法，保留核心年度汇总方法

    // 月度财务汇总
    @Override
    public DrivingBillMonthMessageVo queryMonthAll(String yearAndMonth) {
//        String yearStr = yearAndMonth.substring(0, 4);
//        String monthStr = yearAndMonth.substring(5, yearAndMonth.length() - 1);

//        int month = Integer.parseInt(monthStr);
//        String formattedMonth = String.format("%02d", month);
//        String standardYearMonth = yearStr + "-" + formattedMonth;
//
//        String startDate = standardYearMonth + "-01 00:00:00";
//        LocalDate firstDay = LocalDate.parse(standardYearMonth + "-01");
//        LocalDate lastDay = firstDay.plusMonths(1).minusDays(1);
//        String endDate = lastDay + " 23:59:59";


        //        直接定义固定格式的格式化器
        final DateTimeFormatter FORMATTER_YMD_HMS = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth yearMonth;
        try {
            yearMonth = YearMonth.parse(yearAndMonth, FORMATTER_YMD_HMS);
        } catch (Exception e) {
            throw new RuntimeException("输入时间格式应为yyyy-MM");
        }
        int year = yearMonth.getYear();
        int monthValue = yearMonth.getMonthValue();
        // 1. 查询本月收入
        QueryWrapper<DrivingBillRecord> queryWrapper = new QueryWrapper<>();
//        queryWrapper.between("b.create_time", startDate, endDate);
        queryWrapper.apply("YEAR(b.create_time)={0} AND MONTH(b.create_time)={1}", year, monthValue);
        queryWrapper.eq("b.role_id", 102);
        DrivingBillMonthMessageVo drivingBillMonthMessageVo = drivingBillRecordMapper.queryMonthTotalIncome(queryWrapper);
        if (drivingBillMonthMessageVo == null) {
            drivingBillMonthMessageVo = new DrivingBillMonthMessageVo();
            drivingBillMonthMessageVo.setTotalIncome("0");
        } else if (StringUtils.isEmpty(drivingBillMonthMessageVo.getTotalIncome())) {
            drivingBillMonthMessageVo.setTotalIncome("0");
        }
        BigDecimal MonthTotalIncome = new BigDecimal(drivingBillMonthMessageVo.getTotalIncome());

        // 2. 查询本月支出
        QueryWrapper<DrivingBillRecord> wrapper = new QueryWrapper<>();
//        wrapper.between("b.create_time", startDate, endDate);
        wrapper.apply("YEAR(b.create_time)={0} AND MONTH(b.create_time)={1}", year, monthValue);
        wrapper.eq("b.role_id", 101);
        DrivingBillMonthMessageVo queried = drivingBillRecordMapper.queryMonthTotalExpenditure(wrapper);
        if (queried == null) {
            queried = new DrivingBillMonthMessageVo();
            queried.setTotalExpense("0");
        } else if (StringUtils.isEmpty(queried.getTotalExpense())) {
            queried.setTotalExpense("0");
        }
        BigDecimal MonthTotalExpenditure = new BigDecimal(queried.getTotalExpense());
        BigDecimal subtract = MonthTotalIncome.subtract(MonthTotalExpenditure);

        // 3. 查询本月学员缴费
        QueryWrapper<DrivingBillRecord> qw = new QueryWrapper<>();
//        qw.between("b.create_time", startDate, endDate);
        qw.apply("YEAR(b.create_time)={0} AND MONTH(b.create_time)={1}", year, monthValue);
        qw.eq("b.role_id", 102);
        qw.eq("b.charge_ltem_id", 1);
        DrivingBillMonthMessageVo vo = drivingBillRecordMapper.queryStudentTotalIncome(qw);
        if (vo == null || StringUtils.isEmpty(vo.getStudentPayment())) {
            vo = new DrivingBillMonthMessageVo();
            vo.setStudentPayment("0");
        }
        BigDecimal StudentTotalIncome = new BigDecimal(vo.getStudentPayment());

        // 4. 查询本月其他收入
        QueryWrapper<DrivingBillRecord> qwr = new QueryWrapper<>();
//        qwr.between("b.create_time", startDate, endDate);
        qwr.apply("YEAR(b.create_time)={0} AND MONTH(b.create_time)={1}", year, monthValue);
        qwr.eq("b.role_id", 102);
        qwr.ne("b.charge_ltem_id", 1);
        DrivingBillMonthMessageVo vos = drivingBillRecordMapper.queryNotStudentTotalIncome(qwr);
        if (vos == null || StringUtils.isEmpty(vos.getOtherIncome())) {
            vos = new DrivingBillMonthMessageVo();
            vos.setOtherIncome("0");
        }
        BigDecimal noStudentTotalIncome = new BigDecimal(vos.getOtherIncome());

        // 5. 查询本月学生数量
        QueryWrapper<DrivingBillRecord> wrappered = new QueryWrapper<>();
//        wrappered.between("s.create_time", startDate, endDate);
        wrappered.apply("YEAR(s.create_time)={0} AND MONTH(s.create_time)={1}", year, monthValue);
        DrivingBillYearMessageVo allStudentCount = drivingBillRecordMapper.queryAllStudentCount(wrappered);
        if (allStudentCount == null) {
            allStudentCount = new DrivingBillYearMessageVo();
            allStudentCount.setTotalStudents("0");
        }
        BigDecimal student = new BigDecimal(allStudentCount.getTotalStudents());

        // 6. 计算客单价
        BigDecimal scale = BigDecimal.ZERO;
        if (student.compareTo(BigDecimal.ZERO) > 0) {
            scale = StudentTotalIncome.divide(student, 1, RoundingMode.HALF_UP);
        }

        // 7. 查询上个月数据

//            String lastYearStr = String.valueOf(localDateTime.getYear());
//            String lastMonthFormatted = String.format("%02d", localDateTime.getMonthValue());
//            String lastYearMonth = lastYearStr + "-" + lastMonthFormatted;
//            String lm = lastYearMonth + "-01 00:00:00";
//            LocalDate lastFirstDay = LocalDate.parse(lastYearMonth + "-01");
//            LocalDate lastmonthDay = lastFirstDay.plusMonths(1).minusDays(1);
//            String end = lastmonthDay + " 23:59:59";

        YearMonth lastYearMonth = yearMonth.minusMonths(1);

        // 7.1 查询上月收入
        QueryWrapper<DrivingBillRecord> lastmonth = new QueryWrapper<>();
//            lastmonth.between("b.create_time", lm, end);
        lastmonth.apply("YEAR(b.create_time)={0} AND MONTH(b.create_time)={1}", lastYearMonth.getYear(),
                lastYearMonth.getMonthValue());
        lastmonth.eq("b.role_id", 102);
        DrivingBillMonthMessageVo drivingBillMonthMessageVo3 = drivingBillRecordMapper.queryMonthTotalIncome(lastmonth);
        if (drivingBillMonthMessageVo3 == null || StringUtils.isEmpty(drivingBillMonthMessageVo3.getTotalIncome())) {
            drivingBillMonthMessageVo3 = new DrivingBillMonthMessageVo();
            drivingBillMonthMessageVo3.setTotalIncome("0");
        }
        BigDecimal lastMonthTotalIncome = new BigDecimal(drivingBillMonthMessageVo3.getTotalIncome());

        // 7.2 查询上月支出
        QueryWrapper<DrivingBillRecord> qqq = new QueryWrapper<>();
//            qqq.between("b.create_time", lm, end);
        qqq.apply("YEAR(b.create_time)={0} AND MONTH(b.create_time)={1}", lastYearMonth.getYear(),
                lastYearMonth.getMonthValue());
        qqq.eq("b.role_id", 101);
        DrivingBillMonthMessageVo lastMonthExpenditure = drivingBillRecordMapper.queryMonthTotalExpenditure(qqq);
        if (lastMonthExpenditure == null || StringUtils.isEmpty(lastMonthExpenditure.getTotalExpense())) {
            lastMonthExpenditure = new DrivingBillMonthMessageVo();
            lastMonthExpenditure.setTotalExpense("0");
        }
        BigDecimal lastTotalExpense = new BigDecimal(lastMonthExpenditure.getTotalExpense());

        // 8. 收入增长率
        String divide = "0%";
        if (lastMonthTotalIncome.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal growth = MonthTotalIncome.subtract(lastMonthTotalIncome)
                    .divide(lastMonthTotalIncome, 3, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP);
            divide = growth + "%";
        }

        // 9. 支出增长率
        String string = "0%";
        if (lastTotalExpense.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal expenseGrowth = MonthTotalExpenditure.subtract(lastTotalExpense)
                    .divide(lastTotalExpense, 3, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP);
            string = expenseGrowth + "%";
        }

        // 10. 净利润增长率
        BigDecimal lastMonthNetProfit = lastMonthTotalIncome.subtract(lastTotalExpense);
        String s = "0%";
        if (lastMonthNetProfit.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal profitGrowth = subtract.subtract(lastMonthNetProfit)
                    .divide(lastMonthNetProfit, 3, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP);
            s = profitGrowth + "%";
        }

        // 11. 查询上月学员缴费
        QueryWrapper<DrivingBillRecord> stupay = new QueryWrapper<>();
//            stupay.between("b.create_time", lm, end);
        stupay.apply("YEAR(b.create_time)={0} AND MONTH(b.create_time)={1}", lastYearMonth.getYear(),
                lastYearMonth.getMonthValue());
        stupay.eq("b.role_id", 102);
        stupay.eq("b.charge_ltem_id", 1);
        DrivingBillMonthMessageVo stupayVo = drivingBillRecordMapper.queryStudentTotalIncome(stupay);
        if (stupayVo == null || StringUtils.isEmpty(stupayVo.getStudentPayment())) {
            stupayVo = new DrivingBillMonthMessageVo();
            stupayVo.setStudentPayment("0");
        }
        BigDecimal lastMonthStudentPay = new BigDecimal(stupayVo.getStudentPayment());

        // 12. 学员缴费增长率
        String string1 = "0%";
        if (lastMonthStudentPay.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal studentPaymentGrowth = StudentTotalIncome.subtract(lastMonthStudentPay)
                    .divide(lastMonthStudentPay, 3, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP);
            string1 = studentPaymentGrowth + "%";
        }

        // 13. 查询上月其他收入
        QueryWrapper<DrivingBillRecord> other = new QueryWrapper<>();
//            other.between("b.create_time", lm, end);
        other.apply("YEAR(b.create_time)={0} AND MONTH(b.create_time)={1}", lastYearMonth.getYear(),
                lastYearMonth.getMonthValue());
        other.eq("b.role_id", 102);
        other.ne("b.charge_ltem_id", 1);
        DrivingBillMonthMessageVo studentPay = drivingBillRecordMapper.queryNotStudentTotalIncome(other);
        if (studentPay == null || StringUtils.isEmpty(studentPay.getOtherIncome())) {
            studentPay = new DrivingBillMonthMessageVo();
            studentPay.setOtherIncome("0");
        }
        BigDecimal notStudentTotalIncome = new BigDecimal(studentPay.getOtherIncome());

        // 14. 其他收入增长率
        String string2 = "0%";
        if (notStudentTotalIncome.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal otherIncomeGrowth = noStudentTotalIncome.subtract(notStudentTotalIncome)
                    .divide(notStudentTotalIncome, 3, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP);
            string2 = otherIncomeGrowth + "%";
        }

        // 15. 查询上月学生数量
        QueryWrapper<DrivingBillRecord> count = new QueryWrapper<>();
//            count.between("s.create_time", lm, end);
        count.apply("YEAR(s.create_time)={0} AND MONTH(s.create_time)={1}", lastYearMonth.getYear(),
                lastYearMonth.getMonthValue());
        DrivingBillYearMessageVo lastStudentCount = drivingBillRecordMapper.queryAllStudentCount(count);
        if (lastStudentCount == null) {
            lastStudentCount = new DrivingBillYearMessageVo();
            lastStudentCount.setTotalStudents("0");
        }
        BigDecimal lastCount = new BigDecimal(lastStudentCount.getTotalStudents());

        // 16. 上月客单价
        BigDecimal lastScale = BigDecimal.ZERO;
        if (lastCount.compareTo(BigDecimal.ZERO) > 0) {
            lastScale = lastMonthStudentPay.divide(lastCount, 1, RoundingMode.HALF_UP);
        }

        // 17. 客单价增长率
        String string3 = "0%";
        if (lastScale.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal priceGrowth = scale.subtract(lastScale)
                    .divide(lastScale, 3, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP);
            string3 = priceGrowth + "%";
        }

        // 18. 构造返回结果
        DrivingBillMonthMessageVo drivingBillMonthMessageVo1 = new DrivingBillMonthMessageVo();
        drivingBillMonthMessageVo1.setTotalIncome(MonthTotalIncome.toString());
        drivingBillMonthMessageVo1.setIncomeGrowth(divide);
        drivingBillMonthMessageVo1.setTotalExpense(MonthTotalExpenditure.toString());
        drivingBillMonthMessageVo1.setExpenseGrowth(string);
        drivingBillMonthMessageVo1.setNetProfit(subtract.toString());
        drivingBillMonthMessageVo1.setProfitGrowth(s);
        drivingBillMonthMessageVo1.setOtherIncome(noStudentTotalIncome.toString());
        drivingBillMonthMessageVo1.setOtherIncomeRatio(string2);
        drivingBillMonthMessageVo1.setStudentPayment(StudentTotalIncome.toString());
        drivingBillMonthMessageVo1.setPaymentRatio(string1);
        drivingBillMonthMessageVo1.setAvgCustomerPrice(scale.toString());
        drivingBillMonthMessageVo1.setPriceGrowth(string3);

        return drivingBillMonthMessageVo1;


    }


    @Override
    public List<DrivingGroupMonthVo> queryIncomeTrendByYear(String year) {
        String yearNumber = year.replaceAll("[^0-9]", "");
        int time= Integer.parseInt(yearNumber);
        LocalDateTime startDate = LocalDateTime.of(time, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of((time + 1), 1, 1, 0, 0, 0);

        QueryWrapper<DrivingBillRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("b.create_time", startDate, endDate);
        queryWrapper.eq("b.role_id", 102);

        // 调用Mapper查询
        List<DrivingGroupMonthVo> incomeTrendList = drivingBillRecordMapper.queryIncomeByYearGroupByMonth(queryWrapper);
        if (incomeTrendList == null) {
            incomeTrendList = new ArrayList<>();
        }

        // 补全无数据的月份
        List<DrivingGroupMonthVo> fullMonthList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            String currentMonth = yearNumber + "-" + String.format("%02d", i);
            DrivingGroupMonthVo existVo = incomeTrendList.stream()
                    .filter(vo -> currentMonth.equals(vo.getMonth()))
                    .findFirst()
                    .orElse(new DrivingGroupMonthVo());
            if (existVo.getMonthIncome() == null) {
                existVo.setMonth(currentMonth);
                existVo.setMonthIncome("0");
            }
            fullMonthList.add(existVo);
        }

        return fullMonthList;
    }

    @Override
    public Long selectRoleId(Long userId) {
        Long roleId = drivingBillRecordMapper.selectRoleId(userId);
        return roleId == null ? 0L : roleId;
    }

    @Transactional(rollbackFor =Exception.class )
    @Override
    public int saveDrivingBillRecord(Long payId) {
        //查询支付表
        DrivingPay pay = payMapper.selectById(payId);
        if (pay == null) {
            throw new RuntimeException("支付记录不存在：" + payId);
        }
        Long payPayId = pay.getPayId();
        Long userId = pay.getUserId();
        Long chargeLtemId = pay.getChargeLtemId();

        //查询roleid
        Long roleId = drivingBillRecordMapper.selectRoleId(userId);
        if (roleId == null) {
            throw new RuntimeException("用户" + userId + "无角色信息");
        }

        //构建新的账单记录
        DrivingBillRecord drivingBillRecord = new DrivingBillRecord();
        drivingBillRecord.setUserId(userId);
        drivingBillRecord.setChargeLtemId(chargeLtemId);
        drivingBillRecord.setCreateTime(new Date());
        drivingBillRecord.setDelFlag("0");
        drivingBillRecord.setRoleId(roleId);
        drivingBillRecord.setPayId(payPayId);

        LambdaQueryWrapper<DrivingBillRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DrivingBillRecord::getPayId, payId);
        DrivingBillRecord one = drivingBillRecordMapper.selectOne(lambdaQueryWrapper);
        if (one != null) {
            throw  new RuntimeException("支付记录为" + payId + "已经加入账单记录");
        }

        int save = drivingBillRecordMapper.insert(drivingBillRecord);
        if (save <= 0) {
            throw new RuntimeException("新增账单记录失败");
        }

        pay.setBillStatus("1");
        int b = payMapper.updateById(pay);
        if (b <= 0) {
            throw new RuntimeException("更新支付状态失败");
        }
        return b;
    }
}