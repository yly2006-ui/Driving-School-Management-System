package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingBillRecordQuery;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillRecordListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillYearMessageVo;
import com.mashang.mashangdriving.mapper.manager.DrivingBillRecordMapper;
import com.mashang.mashangdriving.service.manager.IDrivingBillRecordService;
import com.ruoyi.common.utils.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DrivingBillRecordServiceImpl extends ServiceImpl<DrivingBillRecordMapper, DrivingBillRecord> implements IDrivingBillRecordService {
    @Autowired
    private DrivingBillRecordMapper drivingBillRecordMapper;

    @Override
    public Page<DrivingBillRecordListVo> queryBillRecord(Page<DrivingBillRecordListVo> page,
                                                         @Param("query") DrivingBillRecordQuery query) {
        String endTime = query.getEndTime();
        String beginTime = query.getBeginTime();
        String s3 = endTime.replaceAll("[^0-9]", "-");
        String s4 = beginTime.replaceAll("[^0-9]", "-");
        QueryWrapper<DrivingBillRecord>queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(query.getUserName()),
                "driving_student.student_name",query.getUserName());
        queryWrapper.eq(StringUtils.isNotNull(query.getRoleId()),"role.role_id",query.getRoleId());
        queryWrapper.eq(StringUtils.isNotNull(query.getPaymentMethod()),"pay.pay_type",query.getPaymentMethod());
        queryWrapper.orderByDesc("pay.create_time");
        if (StringUtils.isNotEmpty(s4)&&StringUtils.isNotEmpty(s3)){
            String s1 = s4 + " 00:00:00";
            String s2 = s3 + " 23:59:59";
        queryWrapper.between("pay.create_time", s1, s2);
        }
        else if (StringUtils.isNotEmpty(s3)){
            String s = s3 + " 00:00:00";
            String e = s3 + " 23:59:59";
            queryWrapper.between("pay.create_time", s, e);
        }else if (StringUtils.isNotEmpty(s4)){
            String s = s4 + " 00:00:00";
            String e = s4 + " 23:59:59";
            queryWrapper.between("pay.create_time", s, e);
        }else {
            System.out.println("查全部");
        }

        Page<DrivingBillRecordListVo> queried = drivingBillRecordMapper.queryBillRecord(page, queryWrapper);
        for (DrivingBillRecordListVo record : queried.getRecords()) {
            if (record.getRoleName().equals("教练")){
                String s = "-" + record.getAmount();
                record.setFinalAmount(s);
            }else {
                if (record.getRoleName().equals("学员")){
                    String s = "+" + record.getAmount();
                    record.setFinalAmount(s);}
            }
        }
        return queried;
    }

    //年报表
    @Override
    public DrivingBillYearMessageVo queryAll(String year) {
         String yearNumber = year.replaceAll("[^0-9]", "");
        // 修复：将年份转为范围查询（匹配 create_time 的年份）
        QueryWrapper<DrivingBillRecord> queryWrapper = new QueryWrapper<>();
        // 拼接年份的开始（如 2025-01-01 00:00:00）和结束（2025-12-31 23:59:59）
        String startDate = yearNumber + "-01-01 00:00:00";
        String endDate = yearNumber + "-12-31 23:59:59";
        queryWrapper.between("b.create_time", startDate, endDate); // 范围查询年份
        queryWrapper.eq("b.role_id",102);
        DrivingBillYearMessageVo AnnualTotalIncome = drivingBillRecordMapper.queryAnnualTotalIncome(queryWrapper);
        if (AnnualTotalIncome==null){
            throw new RuntimeException("不存在此年份的收入信息");
        }

        QueryWrapper<DrivingBillRecord>wrapper=new QueryWrapper<>();
        wrapper.between("b.create_time", startDate, endDate); // 同样范围查询年份
        wrapper.eq("b.role_id",101);
        DrivingBillYearMessageVo AnnualTotalExpenditure = drivingBillRecordMapper.queryAnnualTotalExpenditure(wrapper);
        System.out.println(AnnualTotalExpenditure);
        if (AnnualTotalExpenditure==null){
            throw new RuntimeException("不存在此年份的支出信息");
        }
        QueryWrapper<DrivingBillRecord>wrappered=new QueryWrapper<>();
        wrappered.between("s.create_time", startDate, endDate); // 同样范围查询年份
        DrivingBillYearMessageVo allStudentCount = drivingBillRecordMapper.queryAllStudentCount(wrappered);
        DrivingBillYearMessageVo YearMessageVo=new DrivingBillYearMessageVo();

        //年支出
        String Expenditure = AnnualTotalExpenditure.getAnnualTotalExpenditure();
        System.out.println("今年支出"+Expenditure);
        //年收入
        String TotalIncome = AnnualTotalIncome.getAnnualTotalIncome();
//        System.out.println("今年收入"+TotalIncome);

        BigDecimal bd1 = new BigDecimal(Expenditure);
        BigDecimal bd2 = new BigDecimal(TotalIncome);

        //年利润
        BigDecimal NetProfit = bd2.subtract(bd1);
//        System.out.println("今年利润"+NetProfit);

        //学员总数
        String totalStudents = allStudentCount.getTotalStudents();
//        System.out.println("今年学生数"+totalStudents);


        //年利率
        BigDecimal TotalIncomed=new BigDecimal(TotalIncome);
        BigDecimal divide = NetProfit.divide(TotalIncomed,3, RoundingMode.HALF_UP);
        BigDecimal profitMarginPercent = divide.multiply(new BigDecimal("100"));
        BigDecimal formattedPercent = profitMarginPercent.setScale(1, RoundingMode.HALF_UP);
        String yearNetProfit = formattedPercent + "%";


        //年收入增长率
        int lastYear = Integer.parseInt(yearNumber)-1;
        String start = lastYear + "-01-01 00:00:00";
        String end = lastYear + "-12-31 23:59:59";
        QueryWrapper<DrivingBillRecord> w = new QueryWrapper<>();
        w.between("b.create_time", start, end); // 范围查询年份
        w.eq("b.role_id",102);
        DrivingBillYearMessageVo lastAnnualTotalIncome = drivingBillRecordMapper.queryAnnualTotalIncome(w);
//        System.out.println("去年收入"+lastAnnualTotalIncome);
        if (lastAnnualTotalIncome==null){
            throw new RuntimeException("不存在前年份收入的信息");
        }

        BigDecimal lastAnnual = new BigDecimal(lastAnnualTotalIncome.getAnnualTotalIncome());
        BigDecimal subtract = bd2.subtract(lastAnnual);
        BigDecimal a = subtract.divide(lastAnnual, 3, RoundingMode.HALF_UP);
        BigDecimal multiply = a.multiply(new BigDecimal("100"));
        BigDecimal lastAnnualTotalIncomegrowthRate = multiply.setScale(1, RoundingMode.HALF_UP);
        String string = lastAnnualTotalIncomegrowthRate + "%";


        //年支出增长率
        QueryWrapper<DrivingBillRecord>r=new QueryWrapper<>();
        r.between("b.create_time", start, end); // 同样范围查询年份
        r.eq("b.role_id",101);
        DrivingBillYearMessageVo e = drivingBillRecordMapper.queryAnnualTotalExpenditure(r);
        System.out.println("去年支出"+AnnualTotalExpenditure.getAnnualTotalExpenditure());
        if (e==null){
            throw new RuntimeException("不存在前年份的支出信息");
        }
        BigDecimal t = new BigDecimal(e.getAnnualTotalExpenditure());
        BigDecimal i = bd1.subtract(t);
        BigDecimal p = i.divide(t, 3, RoundingMode.HALF_UP);
        BigDecimal l = p.multiply(new BigDecimal("100"));
        BigDecimal m = l.setScale(1, RoundingMode.HALF_UP);
        String n = m + "%";


        //去年利润率
        BigDecimal lastyear = lastAnnual.subtract(t);
//        System.out.println("去年利润"+lastyear);
        BigDecimal divide1 = NetProfit.subtract(lastyear).divide(lastyear, 3, RoundingMode.HALF_UP);
        BigDecimal scale1 = divide1.multiply(new BigDecimal(100)).setScale(1, RoundingMode.HALF_UP);
        String s1 = scale1 + "%";

        //学生增长率
        QueryWrapper<DrivingBillRecord>student=new QueryWrapper<>();
        student.between("s.create_time", start, end); // 同样范围查询年份
        DrivingBillYearMessageVo studentTop = drivingBillRecordMapper.queryAllStudentCount(student);
//        System.out.println("去年的学生数"+studentTop.getTotalStudents());
        BigDecimal studentBigDecimal=new BigDecimal(totalStudents);
        BigDecimal lastStudentBigDecimal =new BigDecimal(studentTop.getTotalStudents());
        BigDecimal decimal = studentBigDecimal.subtract(lastStudentBigDecimal).divide(lastStudentBigDecimal,
                3, RoundingMode.HALF_UP);
        BigDecimal multiply1 = decimal.multiply(new BigDecimal("100"));
        BigDecimal scale = multiply1.setScale(1, RoundingMode.HALF_UP);
        String s = scale + "%";



        //利润率变化
        String subtract1 = NetProfit.subtract(lastyear)+"%";

        YearMessageVo.setAnnualTotalExpenditure(Expenditure);
        YearMessageVo.setAnnualTotalIncomelastYear(string);
        YearMessageVo.setAnnualTotalIncome(TotalIncome);
        YearMessageVo.setAnnualTotalExpenditurelastYear(n);


        YearMessageVo.setAnnualNetProfit(String.valueOf(NetProfit));
        YearMessageVo.setAnnualNetProfitlastYear(s1);

        YearMessageVo.setTotalStudents(totalStudents);
        YearMessageVo.setTotalStudentslastYear(s);
        //todo 完成培训的学员
        YearMessageVo.setProfitMargin(yearNetProfit);
        YearMessageVo.setProfitMarginlastYear(subtract1);
        return YearMessageVo;
    }


}
