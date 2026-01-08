package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingBillRecordQuery;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillMonthMessageVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillRecordListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillYearMessageVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingGroupMonthVo;
import com.mashang.mashangdriving.mapper.manager.DrivingBillRecordMapper;
import com.mashang.mashangdriving.mapper.student.DrivingCourseAttributeMapper;
import com.mashang.mashangdriving.service.manager.IDrivingBillRecordService;
import com.ruoyi.common.utils.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DrivingBillRecordServiceImpl extends ServiceImpl<DrivingBillRecordMapper, DrivingBillRecord> implements IDrivingBillRecordService {
    @Autowired
    private DrivingBillRecordMapper drivingBillRecordMapper;
    @Autowired
    private DrivingCourseAttributeMapper drivingCourseAttributeMapper;

    @Override
    public Page<DrivingBillRecordListVo> queryBillRecord(Page<DrivingBillRecordListVo> page,
                                                         @Param("query") DrivingBillRecordQuery query) {
        String endTime = query.getEndTime();
        String beginTime = query.getBeginTime();
        String s3 = endTime.replaceAll("[^0-9]", "-");
        String s4 = beginTime.replaceAll("[^0-9]", "-");
        QueryWrapper<DrivingBillRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(query.getUserName()),
                "driving_student.student_name", query.getUserName());
        queryWrapper.eq(StringUtils.isNotNull(query.getRoleId()), "role.role_id", query.getRoleId());
        queryWrapper.eq(StringUtils.isNotNull(query.getPaymentMethod()), "pay.pay_type", query.getPaymentMethod());
        queryWrapper.orderByDesc("pay.create_time");
        if (StringUtils.isNotEmpty(s4) && StringUtils.isNotEmpty(s3)) {
            String s1 = s4 + " 00:00:00";
            String s2 = s3 + " 23:59:59";
            queryWrapper.between("pay.create_time", s1, s2);
        } else if (StringUtils.isNotEmpty(s3)) {
            String s = s3 + " 00:00:00";
            String e = s3 + " 23:59:59";
            queryWrapper.between("pay.create_time", s, e);
        } else if (StringUtils.isNotEmpty(s4)) {
            String s = s4 + " 00:00:00";
            String e = s4 + " 23:59:59";
            queryWrapper.between("pay.create_time", s, e);
        } else {
            System.out.println("查全部");
        }

        Page<DrivingBillRecordListVo> queried = drivingBillRecordMapper.queryBillRecord(page, queryWrapper);
        for (DrivingBillRecordListVo record : queried.getRecords()) {
            if (record.getRoleName().equals("教练")) {
                String s = "-" + record.getAmount();
                record.setFinalAmount(s);
            } else {
                if (record.getRoleName().equals("学员")) {
                    String s = "+" + record.getAmount();
                    record.setFinalAmount(s);
                }
            }
        }
        return queried;
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

        QueryWrapper<DrivingBillRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("b.create_time", startDate, endDate);
        queryWrapper.eq("b.role_id", 102);
        DrivingBillMonthMessageVo drivingBillMonthMessageVo = drivingBillRecordMapper.queryMonthTotalIncome(queryWrapper);
        if (drivingBillMonthMessageVo.getTotalIncome() == null) {
            throw new RuntimeException("不存在本月的收入信息");
        }
        BigDecimal MonthTotalIncome = new BigDecimal(drivingBillMonthMessageVo.getTotalIncome());

        QueryWrapper<DrivingBillRecord> wrapper = new QueryWrapper<>();
        wrapper.between("b.create_time", startDate, endDate);
        wrapper.eq("b.role_id", 101);
        DrivingBillMonthMessageVo queried = drivingBillRecordMapper.queryMonthTotalExpenditure(wrapper);
        if (queried.getTotalExpense() == null) {
            throw new RuntimeException("不存在本月份的支出信息");
        }
        BigDecimal MonthTotalExpenditure = new BigDecimal(queried.getTotalExpense());

        BigDecimal subtract = MonthTotalIncome.subtract(MonthTotalExpenditure);

        QueryWrapper<DrivingBillRecord> qw = new QueryWrapper<>();
        qw.between("b.create_time", startDate, endDate);
        qw.eq("b.role_id", 102);
        qw.eq("b.charge_ltem_id", 1);
        DrivingBillMonthMessageVo vo = drivingBillRecordMapper.queryStudentTotalIncome(qw);
        if (vo.getStudentPayment() == null) {
            throw new RuntimeException("不存在本月学员缴费");
        }
        BigDecimal StudentTotalIncome = new BigDecimal(vo.getStudentPayment());

        QueryWrapper<DrivingBillRecord> qwr = new QueryWrapper<>();
        qwr.between("b.create_time", startDate, endDate);
        qwr.eq("b.role_id", 102);
        qwr.ne("b.charge_ltem_id", 1);
        DrivingBillMonthMessageVo vos = drivingBillRecordMapper.queryNotStudentTotalIncome(qwr);
        if (vos.getOtherIncome() == null) {
            throw new RuntimeException("不存在其他收入");
        }
        BigDecimal noStudentTotalIncome = new BigDecimal(vos.getOtherIncome());

        QueryWrapper<DrivingBillRecord> wrappered = new QueryWrapper<>();
        wrappered.between("s.create_time", startDate, endDate);
        DrivingBillYearMessageVo allStudentCount = drivingBillRecordMapper.queryAllStudentCount(wrappered);
        if (allStudentCount.getTotalStudents() == null) {
            throw new RuntimeException("本月无学生");
        }
        BigDecimal student = new BigDecimal(allStudentCount.getTotalStudents());

        // 客单价修复：移除多余multiply("100")
        BigDecimal scale = StudentTotalIncome.divide(student, 1, RoundingMode.HALF_UP);

        // 上个月时间修复：自动跨年度
        LocalDate lastMonthFirstDay = firstDay.minusMonths(1);
        String lastYearStr = String.valueOf(lastMonthFirstDay.getYear());
        String lastMonthFormatted = String.format("%02d", lastMonthFirstDay.getMonthValue());
        String lastYearMonth = lastYearStr + "-" + lastMonthFormatted;
        String lm = lastYearMonth + "-01 00:00:00";
        LocalDate lastFirstDay = LocalDate.parse(lastYearMonth + "-01");
        LocalDate lastmonthDay = lastFirstDay.plusMonths(1).minusDays(1);
        String end = lastmonthDay + " 23:59:59";

        QueryWrapper<DrivingBillRecord> lastmonth = new QueryWrapper<>();
        lastmonth.between("b.create_time", lm, end);
        lastmonth.eq("b.role_id", 102);
        DrivingBillMonthMessageVo drivingBillMonthMessageVo3 = drivingBillRecordMapper.queryMonthTotalIncome(lastmonth);
        System.out.println("本月收入" + drivingBillMonthMessageVo3.getTotalIncome());
        if (drivingBillMonthMessageVo3.getTotalIncome() == null) {
            throw new RuntimeException("不存在上个月的收入信息");
        }
        BigDecimal lastMonthTotalIncome = new BigDecimal(drivingBillMonthMessageVo3.getTotalIncome());

        String divide = MonthTotalIncome.subtract(lastMonthTotalIncome).divide(lastMonthTotalIncome, 3,
                RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP) + "%";

        QueryWrapper<DrivingBillRecord> qqq = new QueryWrapper<>();
        qqq.between("b.create_time", lm, end);
        qqq.eq("b.role_id", 101);
        DrivingBillMonthMessageVo lastMonthExpenditure = drivingBillRecordMapper.queryMonthTotalExpenditure(qqq);
        System.out.println("上月支出" + lastMonthExpenditure.getTotalExpense());
        if (queried.getTotalExpense() == null) {
            throw new RuntimeException("不存在上个月份的支出信息");
        }
        BigDecimal lastTotalExpense = new BigDecimal(lastMonthExpenditure.getTotalExpense());

        String string = MonthTotalExpenditure.subtract(lastTotalExpense).divide(lastTotalExpense, 3,
                RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP) + "%";

        BigDecimal decimal = lastMonthTotalIncome.subtract(lastTotalExpense);

        String s = subtract.subtract(decimal).divide(decimal, 3, RoundingMode.HALF_UP).multiply(new BigDecimal("100"))
                .setScale(1, RoundingMode.HALF_UP) + "%";

        QueryWrapper<DrivingBillRecord> stupay = new QueryWrapper<>();
        stupay.between("b.create_time", lm, end);
        stupay.eq("b.role_id", 102);
        stupay.eq("b.charge_ltem_id", 1);
        DrivingBillMonthMessageVo stupayVo = drivingBillRecordMapper.queryStudentTotalIncome(stupay);
        System.out.println("上月学员缴费" + stupayVo.getStudentPayment());
        if (stupayVo.getStudentPayment() == null) {
            throw new RuntimeException("不存在上月学员缴费");
        }
        BigDecimal lastMonthStudentPay = new BigDecimal(stupayVo.getStudentPayment());

        String string1 = StudentTotalIncome.subtract(lastMonthStudentPay).divide(lastMonthStudentPay, 3, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP) + "%";

        QueryWrapper<DrivingBillRecord> other = new QueryWrapper<>();
        other.between("b.create_time", lm, end);
        other.eq("b.role_id", 102);
        other.ne("b.charge_ltem_id", 1);
        DrivingBillMonthMessageVo studentPay = drivingBillRecordMapper.queryNotStudentTotalIncome(other);
        System.out.println("上月其他收入" + studentPay.getOtherIncome());
        if (studentPay.getOtherIncome() == null) {
            throw new RuntimeException("不存在上月其他收入");
        }
        BigDecimal notStudentTotalIncome = new BigDecimal(studentPay.getOtherIncome());

        String string2 = noStudentTotalIncome.subtract(notStudentTotalIncome).divide(noStudentTotalIncome, 3, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP) + "%";

        QueryWrapper<DrivingBillRecord> count = new QueryWrapper<>();
        count.between("s.create_time", lm, end);
        DrivingBillYearMessageVo lastStudentCount = drivingBillRecordMapper.queryAllStudentCount(count);
        System.out.println("shang月学生数" + lastStudentCount.getTotalStudents());
        if (lastStudentCount.getTotalStudents() == null) {
            throw new RuntimeException("shang月无学生");
        }
        BigDecimal lastCount = new BigDecimal(allStudentCount.getTotalStudents());

        BigDecimal lastScale = lastMonthStudentPay.divide(lastCount, 1, RoundingMode.HALF_UP);

        String string3 = scale.subtract(lastScale).divide(lastScale, 3, RoundingMode.HALF_UP).multiply(new BigDecimal("100"))
                .setScale(1, RoundingMode.HALF_UP) + "%";

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

}