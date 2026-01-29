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
import com.mashang.mashangdriving.mapper.student.DrivingCourseAttributeMapper;
import com.mashang.mashangdriving.mapper.student.PayMapper;
import com.mashang.mashangdriving.service.manager.IDrivingBillRecordService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
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

        // 1. 基本条件
        queryWrapper.like(StringUtils.isNotEmpty(query.getUserName()),
                "driving_student.student_name", query.getUserName());
        String roleId = query.getRoleId();
        if (!"0".equals(roleId)) {
            queryWrapper.eq(StringUtils.isNotNull(query.getRoleId()), "role.role_id", roleId);
        }
        queryWrapper.eq(StringUtils.isNotNull(query.getPaymentMethod()), "pay.pay_type", query.getPaymentMethod());
        queryWrapper.orderByDesc("r.create_time");

        // 2. 处理时间条件
        handleTimeCondition(query, queryWrapper);

        // 3. 执行查询
        Page<DrivingBillRecordListVo> queried = drivingBillRecordMapper.queryBillRecord(page, queryWrapper);

        // 4. 处理金额格式
        if (queried != null && CollectionUtils.isNotEmpty(queried.getRecords())) {
            for (DrivingBillRecordListVo record : queried.getRecords()) {
                if ("教练".equals(record.getRoleName())) {
                    record.setFinalAmount("-" + record.getAmount());
                } else if ("学员".equals(record.getRoleName())) {
                    record.setFinalAmount("+" + record.getAmount());
                }
            }
        }

        return queried;
    }

    /**
     * 处理时间条件
     * 逻辑：
     * 1. 都没传时间 → 查全部
     * 2. 只有开始时间 → 查开始时间之后的所有记录
     * 3. 只有结束时间 → 查结束时间之前的所有记录
     * 4. 都有 → 查时间段内的记录
     */
    private void handleTimeCondition(DrivingBillRecordQuery query, QueryWrapper<DrivingBillRecord> queryWrapper) {
        String endTime = query.getEndTime();
        String beginTime = query.getBeginTime();

        // 1. 都有：查询时间段内的记录
        if (StringUtils.isNotEmpty(beginTime) && StringUtils.isNotEmpty(endTime)) {
            String startDate = parseDate(beginTime) + " 00:00:00";
            String endDate = parseDate(endTime) + " 23:59:59";
            queryWrapper.between("pay.create_time", startDate, endDate);
        }
        // 2. 只有结束时间：查询结束时间之前的所有记录
        else if (StringUtils.isNotEmpty(endTime)) {
            String endDate = parseDate(endTime) + " 23:59:59";
            queryWrapper.le("pay.create_time", endDate);  // create_time <= endDate
        }
        // 3. 只有开始时间：查询开始时间之后的所有记录
        else if (StringUtils.isNotEmpty(beginTime)) {
            String startDate = parseDate(beginTime) + " 00:00:00";
            queryWrapper.ge("pay.create_time", startDate);  // create_time >= startDate
        }
        // 4. 都没传：不加时间条件，查全部
    }

    /**
     * 解析日期字符串，统一格式为yyyy-MM-dd，并自动补零
     * 支持格式：
     * 1. yyyy-MM-dd (2024-01-05)
     * 2. yyyy/MM/dd (2024/1/5)
     * 3. yyyy年MM月dd日 (2024年1月5日)
     * 4. yyyyMMdd (20240105)
     */
    private String parseDate(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return "";
        }

        try {
            // 清理所有非数字字符
            String cleanDate = dateStr.replaceAll("[^0-9]", "");

            if (cleanDate.length() >= 8) {
                // 格式化为 yyyy-MM-dd，自动补零
                String year = cleanDate.substring(0, 4);

                // 处理月份（可能需要补零）
                String month = cleanDate.substring(4, 6);
                if (month.length() == 1) {
                    month = "0" + month;
                } else if (month.startsWith("0")) {
                    // 如果已经是两位数，但第一个是0，确保格式正确
                    month = String.format("%02d", Integer.parseInt(month));
                }

                // 处理日期（可能需要补零）
                String day = cleanDate.substring(6, 8);
                if (day.length() == 1) {
                    day = "0" + day;
                } else if (day.startsWith("0")) {
                    // 如果已经是两位数，但第一个是0，确保格式正确
                    day = String.format("%02d", Integer.parseInt(day));
                }

                return year + "-" + month + "-" + day;
            } else if (cleanDate.length() == 7) {
                // 处理类似 2024015 这种格式（2024年1月5日去掉非数字后）
                String year = cleanDate.substring(0, 4);

                // 处理月份和日期（单个数字）
                String monthPart = cleanDate.substring(4, 5);
                String dayPart = cleanDate.substring(5);

                // 自动补零
                String month = String.format("%02d", Integer.parseInt(monthPart));
                String day = String.format("%02d", Integer.parseInt(dayPart));

                return year + "-" + month + "-" + day;
            } else if (cleanDate.length() == 6) {
                // 处理类似 240105 这种短格式
                if (cleanDate.startsWith("2")) {
                    // 假设是 20240105 缺少了两位
                    String year = "20" + cleanDate.substring(0, 2);
                    String month = String.format("%02d", Integer.parseInt(cleanDate.substring(2, 4)));
                    String day = String.format("%02d", Integer.parseInt(cleanDate.substring(4)));
                    return year + "-" + month + "-" + day;
                }
            }
        } catch (Exception e) {
            log.error("日期解析错误: {}");
            // 如果解析失败，尝试其他方式
            return tryOtherDateFormat(dateStr);
        }

        return dateStr; // 如果都不匹配，返回原字符串
    }

    /**
     * 尝试其他日期格式解析
     */
    private String tryOtherDateFormat(String dateStr) {
        try {
            // 尝试解析常见格式
            if (dateStr.contains("年") && dateStr.contains("月") && dateStr.contains("日")) {
                // 处理中文格式：2024年1月5日
                Pattern pattern = Pattern.compile("(\\d{4})年(\\d{1,2})月(\\d{1,2})日");
                Matcher matcher = pattern.matcher(dateStr);
                if (matcher.find()) {
                    String year = matcher.group(1);
                    String month = String.format("%02d", Integer.parseInt(matcher.group(2)));
                    String day = String.format("%02d", Integer.parseInt(matcher.group(3)));
                    return year + "-" + month + "-" + day;
                }
            } else if (dateStr.contains("-")) {
                // 处理 2024-1-5 这种格式
                String[] parts = dateStr.split("-");
                if (parts.length == 3) {
                    String year = parts[0];
                    String month = String.format("%02d", Integer.parseInt(parts[1]));
                    String day = String.format("%02d", Integer.parseInt(parts[2]));
                    return year + "-" + month + "-" + day;
                }
            } else if (dateStr.contains("/")) {
                // 处理 2024/1/5 这种格式
                String[] parts = dateStr.split("/");
                if (parts.length == 3) {
                    String year = parts[0];
                    String month = String.format("%02d", Integer.parseInt(parts[1]));
                    String day = String.format("%02d", Integer.parseInt(parts[2]));
                    return year + "-" + month + "-" + day;
                }
            }
        } catch (Exception e) {
            log.error("其他日期格式解析错误: {}");
        }

        // 如果所有解析都失败，返回原字符串，但记录警告
        log.warn("无法解析的日期格式: {}");
        return dateStr;
    }
    //年报表
    @Override
    public DrivingBillYearMessageVo queryAll(String year) {
        String yearNumber = year.replaceAll("[^0-9]", "");
        QueryWrapper<DrivingBillRecord> queryWrapper = new QueryWrapper<>();
        String startDate = yearNumber + "-01-01 00:00:00";
        String endDate = yearNumber + "-12-31 23:59:59";
        queryWrapper.between("b.create_time", startDate, endDate);
        queryWrapper.eq("b.role_id", 102);
        DrivingBillYearMessageVo AnnualTotalIncome = drivingBillRecordMapper.queryAnnualTotalIncome(queryWrapper);
        if (AnnualTotalIncome == null) {
            throw new RuntimeException("不存在此年份的收入信息");
        }

        QueryWrapper<DrivingBillRecord> wrapper = new QueryWrapper<>();
        wrapper.between("b.create_time", startDate, endDate);
        wrapper.eq("b.role_id", 101);
        DrivingBillYearMessageVo AnnualTotalExpenditure = drivingBillRecordMapper.queryAnnualTotalExpenditure(wrapper);
        System.out.println(AnnualTotalExpenditure);
        if (AnnualTotalExpenditure == null) {
            throw new RuntimeException("不存在此年份的支出信息");
        }
        QueryWrapper<DrivingBillRecord> wrappered = new QueryWrapper<>();
        wrappered.between("s.create_time", startDate, endDate);
        DrivingBillYearMessageVo allStudentCount = drivingBillRecordMapper.queryAllStudentCount(wrappered);
        DrivingBillYearMessageVo YearMessageVo = new DrivingBillYearMessageVo();

        String Expenditure = AnnualTotalExpenditure.getAnnualTotalExpenditure();
//        System.out.println("今年支出"+Expenditure);
        String TotalIncome = AnnualTotalIncome.getAnnualTotalIncome();

        BigDecimal bd1 = new BigDecimal(Expenditure);
        BigDecimal bd2 = new BigDecimal(TotalIncome);

        BigDecimal NetProfit = bd2.subtract(bd1);

        String totalStudents = allStudentCount.getTotalStudents();
        System.out.println("今年一共存在的学生数量" + totalStudents);
        BigDecimal TotalIncomed = new BigDecimal(TotalIncome);
        BigDecimal divide = NetProfit.divide(TotalIncomed, 3, RoundingMode.HALF_UP);
        BigDecimal profitMarginPercent = divide.multiply(new BigDecimal("100"));
        BigDecimal formattedPercent = profitMarginPercent.setScale(1, RoundingMode.HALF_UP);
        String yearNetProfit = formattedPercent + "%";

        int lastYear = Integer.parseInt(yearNumber) - 1;
        String start = lastYear + "-01-01 00:00:00";
        String end = lastYear + "-12-31 23:59:59";
        QueryWrapper<DrivingBillRecord> w = new QueryWrapper<>();
        w.between("b.create_time", start, end);
        w.eq("b.role_id", 102);
        DrivingBillYearMessageVo lastAnnualTotalIncome = drivingBillRecordMapper.queryAnnualTotalIncome(w);
        if (lastAnnualTotalIncome == null) {
            throw new RuntimeException("不存在前年份收入的信息");
        }

        BigDecimal lastAnnual = new BigDecimal(lastAnnualTotalIncome.getAnnualTotalIncome());
        BigDecimal subtract = bd2.subtract(lastAnnual);
        BigDecimal a = subtract.divide(lastAnnual, 3, RoundingMode.HALF_UP);
        BigDecimal multiply = a.multiply(new BigDecimal("100"));
        BigDecimal lastAnnualTotalIncomegrowthRate = multiply.setScale(1, RoundingMode.HALF_UP);
        String string = lastAnnualTotalIncomegrowthRate + "%";

        QueryWrapper<DrivingBillRecord> r = new QueryWrapper<>();
        r.between("b.create_time", start, end);
        r.eq("b.role_id", 101);
        DrivingBillYearMessageVo e = drivingBillRecordMapper.queryAnnualTotalExpenditure(r);
        System.out.println("去年支出" + AnnualTotalExpenditure.getAnnualTotalExpenditure());
        if (e == null) {
            throw new RuntimeException("不存在前年份的支出信息");
        }
        BigDecimal t = new BigDecimal(e.getAnnualTotalExpenditure());
        BigDecimal i = bd1.subtract(t);
        BigDecimal p = i.divide(t, 3, RoundingMode.HALF_UP);
        BigDecimal l = p.multiply(new BigDecimal("100"));
        BigDecimal m = l.setScale(1, RoundingMode.HALF_UP);
        String n = m + "%";

        BigDecimal lastyear = lastAnnual.subtract(t);
        BigDecimal divide1 = NetProfit.subtract(lastyear).divide(lastyear, 3, RoundingMode.HALF_UP);
        BigDecimal scale1 = divide1.multiply(new BigDecimal(100)).setScale(1, RoundingMode.HALF_UP);
        String s1 = scale1 + "%";

        QueryWrapper<DrivingBillRecord> student = new QueryWrapper<>();
        student.between("s.create_time", start, end);
        DrivingBillYearMessageVo studentTop = drivingBillRecordMapper.queryAllStudentCount(student);
        BigDecimal studentBigDecimal = new BigDecimal(totalStudents);
        BigDecimal lastStudentBigDecimal = new BigDecimal(studentTop.getTotalStudents());
        BigDecimal decimal = studentBigDecimal.subtract(lastStudentBigDecimal).divide(lastStudentBigDecimal,
                3, RoundingMode.HALF_UP);
        BigDecimal multiply1 = decimal.multiply(new BigDecimal("100"));
        BigDecimal scale = multiply1.setScale(1, RoundingMode.HALF_UP);
        String s = scale + "%";

        String subtract1 = NetProfit.subtract(lastyear) + "%";

        //完成培训学生数量
        QueryWrapper<DrivingBillRecord> finishedStudent = new QueryWrapper<>();
        finishedStudent.between("s.create_time", startDate, endDate);
        finishedStudent.eq("s.status", "1");
        Long finisheded = drivingBillRecordMapper.finishedStudent(finishedStudent);
        System.out.println("完成培训学生数量" + finisheded);

        BigDecimal bigDecimal = new BigDecimal(finisheded);

        //通过培训率
        String setScale = bigDecimal.divide(studentBigDecimal, 3, RoundingMode.HALF_UP).multiply(new BigDecimal("100"))
                .setScale(1, RoundingMode.HALF_UP) + "%";


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
        YearMessageVo.setCompletedTrainingFinsh((setScale));
        return YearMessageVo;
    }

    @Override
    public List<DrivingBillYearMessageVo> DrivingBillYearMessageVo(String year) {
        String yearNumber = year.replaceAll("[^0-9]", "");
        QueryWrapper<DrivingBillRecord> queryWrapper = new QueryWrapper<>();
        String startDate = yearNumber + "-01-01 00:00:00";
        String endDate = yearNumber + "-12-31 23:59:59";
        queryWrapper.between("b.create_time", startDate, endDate);
        queryWrapper.eq("b.role_id", 102);
        DrivingBillYearMessageVo AnnualTotalIncome = drivingBillRecordMapper.queryAnnualTotalIncome(queryWrapper);
        if (AnnualTotalIncome == null) {
            throw new RuntimeException("不存在此年份的收入信息");
        }

        QueryWrapper<DrivingBillRecord> wrapper = new QueryWrapper<>();
        wrapper.between("b.create_time", startDate, endDate);
        wrapper.eq("b.role_id", 101);
        DrivingBillYearMessageVo AnnualTotalExpenditure = drivingBillRecordMapper.queryAnnualTotalExpenditure(wrapper);
        System.out.println(AnnualTotalExpenditure);
        if (AnnualTotalExpenditure == null) {
            throw new RuntimeException("不存在此年份的支出信息");
        }
        QueryWrapper<DrivingBillRecord> wrappered = new QueryWrapper<>();
        wrappered.between("s.create_time", startDate, endDate);
        DrivingBillYearMessageVo allStudentCount = drivingBillRecordMapper.queryAllStudentCount(wrappered);
        DrivingBillYearMessageVo YearMessageVo = new DrivingBillYearMessageVo();

        String Expenditure = AnnualTotalExpenditure.getAnnualTotalExpenditure();
//        System.out.println("今年支出"+Expenditure);
        String TotalIncome = AnnualTotalIncome.getAnnualTotalIncome();

        BigDecimal bd1 = new BigDecimal(Expenditure);
        BigDecimal bd2 = new BigDecimal(TotalIncome);

        BigDecimal NetProfit = bd2.subtract(bd1);

        String totalStudents = allStudentCount.getTotalStudents();
        System.out.println("今年一共存在的学生数量" + totalStudents);
        BigDecimal TotalIncomed = new BigDecimal(TotalIncome);
        BigDecimal divide = NetProfit.divide(TotalIncomed, 3, RoundingMode.HALF_UP);
        BigDecimal profitMarginPercent = divide.multiply(new BigDecimal("100"));
        BigDecimal formattedPercent = profitMarginPercent.setScale(1, RoundingMode.HALF_UP);
        String yearNetProfit = formattedPercent + "%";

        int lastYear = Integer.parseInt(yearNumber) - 1;
        String start = lastYear + "-01-01 00:00:00";
        String end = lastYear + "-12-31 23:59:59";
        QueryWrapper<DrivingBillRecord> w = new QueryWrapper<>();
        w.between("b.create_time", start, end);
        w.eq("b.role_id", 102);
        DrivingBillYearMessageVo lastAnnualTotalIncome = drivingBillRecordMapper.queryAnnualTotalIncome(w);
        if (lastAnnualTotalIncome == null) {
            throw new RuntimeException("不存在前年份收入的信息");
        }

        BigDecimal lastAnnual = new BigDecimal(lastAnnualTotalIncome.getAnnualTotalIncome());
        BigDecimal subtract = bd2.subtract(lastAnnual);
        BigDecimal a = subtract.divide(lastAnnual, 3, RoundingMode.HALF_UP);
        BigDecimal multiply = a.multiply(new BigDecimal("100"));
        BigDecimal lastAnnualTotalIncomegrowthRate = multiply.setScale(1, RoundingMode.HALF_UP);
        String string = lastAnnualTotalIncomegrowthRate + "%";

        QueryWrapper<DrivingBillRecord> r = new QueryWrapper<>();
        r.between("b.create_time", start, end);
        r.eq("b.role_id", 101);
        DrivingBillYearMessageVo e = drivingBillRecordMapper.queryAnnualTotalExpenditure(r);
        System.out.println("去年支出" + AnnualTotalExpenditure.getAnnualTotalExpenditure());
        if (e == null) {
            throw new RuntimeException("不存在前年份的支出信息");
        }
        BigDecimal t = new BigDecimal(e.getAnnualTotalExpenditure());
        BigDecimal i = bd1.subtract(t);
        BigDecimal p = i.divide(t, 3, RoundingMode.HALF_UP);
        BigDecimal l = p.multiply(new BigDecimal("100"));
        BigDecimal m = l.setScale(1, RoundingMode.HALF_UP);
        String n = m + "%";

        BigDecimal lastyear = lastAnnual.subtract(t);
        BigDecimal divide1 = NetProfit.subtract(lastyear).divide(lastyear, 3, RoundingMode.HALF_UP);
        BigDecimal scale1 = divide1.multiply(new BigDecimal(100)).setScale(1, RoundingMode.HALF_UP);
        String s1 = scale1 + "%";

        QueryWrapper<DrivingBillRecord> student = new QueryWrapper<>();
        student.between("s.create_time", start, end);
        DrivingBillYearMessageVo studentTop = drivingBillRecordMapper.queryAllStudentCount(student);
        BigDecimal studentBigDecimal = new BigDecimal(totalStudents);
        BigDecimal lastStudentBigDecimal = new BigDecimal(studentTop.getTotalStudents());
        BigDecimal decimal = studentBigDecimal.subtract(lastStudentBigDecimal).divide(lastStudentBigDecimal,
                3, RoundingMode.HALF_UP);
        BigDecimal multiply1 = decimal.multiply(new BigDecimal("100"));
        BigDecimal scale = multiply1.setScale(1, RoundingMode.HALF_UP);
        String s = scale + "%";

        String subtract1 = NetProfit.subtract(lastyear) + "%";

        //完成培训学生数量
        QueryWrapper<DrivingBillRecord> finishedStudent = new QueryWrapper<>();
        finishedStudent.between("s.create_time", startDate, endDate);
        finishedStudent.eq("s.status", "1");
        Long finisheded = drivingBillRecordMapper.finishedStudent(finishedStudent);
        System.out.println("完成培训学生数量" + finisheded);

        BigDecimal bigDecimal = new BigDecimal(finisheded);

        //通过培训率
        String setScale = bigDecimal.divide(studentBigDecimal, 3, RoundingMode.HALF_UP).multiply(new BigDecimal("100"))
                .setScale(1, RoundingMode.HALF_UP) + "%";


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
        YearMessageVo.setCompletedTrainingFinsh((setScale));
        return (List<DrivingBillYearMessageVo>) YearMessageVo;
    }

    @Override
    public DrivingBillMonthMessageVo queryMonthAll(String yearAndMonth) {
        String yearStr = yearAndMonth.substring(0, 4);
        String monthStr = yearAndMonth.substring(5, yearAndMonth.length() - 1);

        int month = Integer.parseInt(monthStr);
        String formattedMonth = String.format("%02d", month);
        String standardYearMonth = yearStr + "-" + formattedMonth;

        String startDate = standardYearMonth + "-01 00:00:00";
        LocalDate firstDay = LocalDate.parse(standardYearMonth + "-01");
        LocalDate lastDay = firstDay.plusMonths(1).minusDays(1);
        String endDate = lastDay + " 23:59:59";

        // 1. 查询本月收入
        QueryWrapper<DrivingBillRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("b.create_time", startDate, endDate);
        queryWrapper.eq("b.role_id", 102);
        DrivingBillMonthMessageVo drivingBillMonthMessageVo = drivingBillRecordMapper.queryMonthTotalIncome(queryWrapper);

        // 防止NPE
        if (drivingBillMonthMessageVo == null) {
            drivingBillMonthMessageVo = new DrivingBillMonthMessageVo();
            drivingBillMonthMessageVo.setTotalIncome("0");
        } else if (StringUtils.isEmpty(drivingBillMonthMessageVo.getTotalIncome())) {
            drivingBillMonthMessageVo.setTotalIncome("0");
        }

        BigDecimal MonthTotalIncome = new BigDecimal(drivingBillMonthMessageVo.getTotalIncome());

        // 2. 查询本月支出
        QueryWrapper<DrivingBillRecord> wrapper = new QueryWrapper<>();
        wrapper.between("b.create_time", startDate, endDate);
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
        qw.between("b.create_time", startDate, endDate);
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
        qwr.between("b.create_time", startDate, endDate);
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
        wrappered.between("s.create_time", startDate, endDate);
        DrivingBillYearMessageVo allStudentCount = drivingBillRecordMapper.queryAllStudentCount(wrappered);

        if (allStudentCount == null || StringUtils.isEmpty(allStudentCount.getTotalStudents())) {
            allStudentCount = new DrivingBillYearMessageVo();
            allStudentCount.setTotalStudents("0");
        }
        BigDecimal student = new BigDecimal(allStudentCount.getTotalStudents());

        // 6. 计算客单价（防止除零）
        BigDecimal scale = BigDecimal.ZERO;
        if (student.compareTo(BigDecimal.ZERO) > 0) {
            scale = StudentTotalIncome.divide(student, 1, RoundingMode.HALF_UP);
        }

        // 7. 查询上个月数据
        LocalDate lastMonthFirstDay = firstDay.minusMonths(1);
        String lastYearStr = String.valueOf(lastMonthFirstDay.getYear());
        String lastMonthFormatted = String.format("%02d", lastMonthFirstDay.getMonthValue());
        String lastYearMonth = lastYearStr + "-" + lastMonthFormatted;
        String lm = lastYearMonth + "-01 00:00:00";
        LocalDate lastFirstDay = LocalDate.parse(lastYearMonth + "-01");
        LocalDate lastmonthDay = lastFirstDay.plusMonths(1).minusDays(1);
        String end = lastmonthDay + " 23:59:59";

        // 7.1 查询上月收入
        QueryWrapper<DrivingBillRecord> lastmonth = new QueryWrapper<>();
        lastmonth.between("b.create_time", lm, end);
        lastmonth.eq("b.role_id", 102);
        DrivingBillMonthMessageVo drivingBillMonthMessageVo3 = drivingBillRecordMapper.queryMonthTotalIncome(lastmonth);

        if (drivingBillMonthMessageVo3 == null || StringUtils.isEmpty(drivingBillMonthMessageVo3.getTotalIncome())) {
            // 上个月没有数据，抛出自定义异常
            throw new RuntimeException("不存在上个月的收入信息");
        }
        BigDecimal lastMonthTotalIncome = new BigDecimal(drivingBillMonthMessageVo3.getTotalIncome());

        // 7.2 查询上月支出
        QueryWrapper<DrivingBillRecord> qqq = new QueryWrapper<>();
        qqq.between("b.create_time", lm, end);
        qqq.eq("b.role_id", 101);
        DrivingBillMonthMessageVo lastMonthExpenditure = drivingBillRecordMapper.queryMonthTotalExpenditure(qqq);

        if (lastMonthExpenditure == null || StringUtils.isEmpty(lastMonthExpenditure.getTotalExpense())) {
            // 上个月没有支出数据
            throw new RuntimeException("不存在上个月的支出信息");
        }
        BigDecimal lastTotalExpense = new BigDecimal(lastMonthExpenditure.getTotalExpense());

        // 8. 计算增长率（防止除零）
        String divide = "0%";
        if (lastMonthTotalIncome.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal growth = MonthTotalIncome.subtract(lastMonthTotalIncome)
                    .divide(lastMonthTotalIncome, 3, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .setScale(1, RoundingMode.HALF_UP);
            divide = growth + "%";
        }

        // 9. 计算支出增长率
        String string = "0%";
        if (lastTotalExpense.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal expenseGrowth = MonthTotalExpenditure.subtract(lastTotalExpense)
                    .divide(lastTotalExpense, 3, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .setScale(1, RoundingMode.HALF_UP);
            string = expenseGrowth + "%";
        }

        BigDecimal lastMonthNetProfit = lastMonthTotalIncome.subtract(lastTotalExpense);

        // 10. 计算净利润增长率
        String s = "0%";
        if (lastMonthNetProfit.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal profitGrowth = subtract.subtract(lastMonthNetProfit)
                    .divide(lastMonthNetProfit, 3, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .setScale(1, RoundingMode.HALF_UP);
            s = profitGrowth + "%";
        }

        // 11. 查询上月学员缴费
        QueryWrapper<DrivingBillRecord> stupay = new QueryWrapper<>();
        stupay.between("b.create_time", lm, end);
        stupay.eq("b.role_id", 102);
        stupay.eq("b.charge_ltem_id", 1);
        DrivingBillMonthMessageVo stupayVo = drivingBillRecordMapper.queryStudentTotalIncome(stupay);

        if (stupayVo == null || StringUtils.isEmpty(stupayVo.getStudentPayment())) {
            throw new RuntimeException("不存在上月学员缴费");
        }
        BigDecimal lastMonthStudentPay = new BigDecimal(stupayVo.getStudentPayment());

        // 12. 计算学员缴费增长率
        String string1 = "0%";
        if (lastMonthStudentPay.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal studentPaymentGrowth = StudentTotalIncome.subtract(lastMonthStudentPay)
                    .divide(lastMonthStudentPay, 3, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .setScale(1, RoundingMode.HALF_UP);
            string1 = studentPaymentGrowth + "%";
        }

        // 13. 查询上月其他收入
        QueryWrapper<DrivingBillRecord> other = new QueryWrapper<>();
        other.between("b.create_time", lm, end);
        other.eq("b.role_id", 102);
        other.ne("b.charge_ltem_id", 1);
        DrivingBillMonthMessageVo studentPay = drivingBillRecordMapper.queryNotStudentTotalIncome(other);

        if (studentPay == null || StringUtils.isEmpty(studentPay.getOtherIncome())) {
            throw new RuntimeException("不存在上月其他收入");
        }
        BigDecimal notStudentTotalIncome = new BigDecimal(studentPay.getOtherIncome());

        // 14. 计算其他收入增长率
        String string2 = "0%";
        if (notStudentTotalIncome.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal otherIncomeGrowth = noStudentTotalIncome.subtract(notStudentTotalIncome)
                    .divide(notStudentTotalIncome, 3, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .setScale(1, RoundingMode.HALF_UP);
            string2 = otherIncomeGrowth + "%";
        }

        // 15. 查询上月学生数量
        QueryWrapper<DrivingBillRecord> count = new QueryWrapper<>();
        count.between("s.create_time", lm, end);
        DrivingBillYearMessageVo lastStudentCount = drivingBillRecordMapper.queryAllStudentCount(count);

        if (lastStudentCount == null || StringUtils.isEmpty(lastStudentCount.getTotalStudents())) {
            throw new RuntimeException("上月无学生");
        }
        BigDecimal lastCount = new BigDecimal(lastStudentCount.getTotalStudents());

        // 16. 计算上月客单价
        BigDecimal lastScale = BigDecimal.ZERO;
        if (lastCount.compareTo(BigDecimal.ZERO) > 0) {
            lastScale = lastMonthStudentPay.divide(lastCount, 1, RoundingMode.HALF_UP);
        }

        // 17. 计算客单价增长率
        String string3 = "0%";
        if (lastScale.compareTo(BigDecimal.ZERO) > 0 && scale.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal priceGrowth = scale.subtract(lastScale)
                    .divide(lastScale, 3, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .setScale(1, RoundingMode.HALF_UP);
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
        // 构造该年的时间范围（如2025-01-01 ~ 2025-12-31）
        String startDate = yearNumber + "-01-01 00:00:00";
        String endDate = yearNumber + "-12-31 23:59:59";

        QueryWrapper<DrivingBillRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("b.create_time", startDate, endDate); // 限定年份
        queryWrapper.eq("b.role_id", 102); // 限定收入（role_id=102对应收入）

        // 调用新增的Mapper方法，按年月分组查询
        List<DrivingGroupMonthVo> incomeTrendList = drivingBillRecordMapper.queryIncomeByYearGroupByMonth(queryWrapper);

        // 补全“无数据的月份”（避免前端图表断档）
        List<DrivingGroupMonthVo> fullMonthList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            String currentMonth = yearNumber + "-" + String.format("%02d", i); // 格式化为2025-01
            // 查找该月是否有数据
            DrivingGroupMonthVo existVo = incomeTrendList.stream()
                    .filter(vo -> currentMonth.equals(vo.getMonth()))
                    .findFirst()
                    .orElse(new DrivingGroupMonthVo());
            // 无数据则收入设为0
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
        return drivingBillRecordMapper.selectRoleId(userId);
    }


    @Transactional(rollbackFor =Exception.class )
    @Override
    public int saveDrivingBillRecord(Long payId) {
        //查询支付表
        DrivingPay pay = payMapper.selectById(payId);
        Long payPayId = pay.getPayId();
        Long userId = pay.getUserId();
        Long chargeLtemId = pay.getChargeLtemId();
        //查询roleid
        Long roleId = drivingBillRecordMapper.selectRoleId(userId);
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
        int b;
        if (save<0) {
            throw new RuntimeException("新增失败");
        } else {
            pay.setBillStatus("1");
            b = payMapper.updateById(pay);
        }
        return b;
    }

}