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


        QueryWrapper<DrivingBillRecord>queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(query.getUserName()),
                "driving_student.student_name",query.getUserName());
        queryWrapper.eq(StringUtils.isNotNull(query.getRoleId()),"role.role_id",query.getRoleId());
        queryWrapper.eq(StringUtils.isNotNull(query.getPaymentMethod()),"pay.pay_type",query.getPaymentMethod());
        queryWrapper.orderByDesc("pay.create_time");
        if (StringUtils.isNotEmpty(beginTime)&&StringUtils.isNotEmpty(endTime)){
            String s1 = beginTime + " 00:00:00";
            String s2 = endTime + " 23:59:59";
        queryWrapper.between("pay.create_time", s1, s2);
        }
        else if (StringUtils.isNotEmpty(endTime)){
            String s = endTime + " 00:00:00";
            String e = endTime + " 23:59:59";
            queryWrapper.between("pay.create_time", s, e);
        }else if (StringUtils.isNotEmpty(beginTime)){
            String s = beginTime + " 00:00:00";
            String e = beginTime + " 23:59:59";
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

    @Override
    public DrivingBillYearMessageVo queryAll(Integer year) {
        // 修复：将年份转为范围查询（匹配 create_time 的年份）
        QueryWrapper<DrivingBillRecord> queryWrapper = new QueryWrapper<>();
        // 拼接年份的开始（如 2025-01-01 00:00:00）和结束（2025-12-31 23:59:59）
        String startDate = year + "-01-01 00:00:00";
        String endDate = year + "-12-31 23:59:59";
        queryWrapper.between("b.create_time", startDate, endDate); // 范围查询年份
        queryWrapper.eq("b.role_id",102);
        DrivingBillYearMessageVo AnnualTotalIncome = drivingBillRecordMapper.queryAnnualTotalIncome(queryWrapper);
        if (AnnualTotalIncome==null){
            throw new RuntimeException("不存在此年份的信息");
        }


        QueryWrapper<DrivingBillRecord>wrapper=new QueryWrapper<>();
        wrapper.between("b.create_time", startDate, endDate); // 同样范围查询年份
        wrapper.eq("b.role_id",101);
        DrivingBillYearMessageVo AnnualTotalExpenditure = drivingBillRecordMapper.queryAnnualTotalExpenditure(wrapper);

        QueryWrapper<DrivingBillRecord>wrappered=new QueryWrapper<>();
        wrappered.between("b.create_time", startDate, endDate); // 同样范围查询年份
        DrivingBillYearMessageVo allStudentCount = drivingBillRecordMapper.queryAllStudentCount(wrappered);
        DrivingBillYearMessageVo YearMessageVo=new DrivingBillYearMessageVo();

        //年支出
        String Expenditure = AnnualTotalExpenditure.getAnnualTotalExpenditure();
        //年收入
        String TotalIncome = AnnualTotalIncome.getAnnualTotalIncome();

        BigDecimal bd1 = new BigDecimal(Expenditure);
        BigDecimal bd2 = new BigDecimal(TotalIncome);

        //年利润
        BigDecimal NetProfit = bd2.subtract(bd1);

        //学员总数
        String totalStudents = allStudentCount.getTotalStudents();



        //年利率
        BigDecimal TotalIncomed=new BigDecimal(TotalIncome);
        BigDecimal divide = NetProfit.divide(TotalIncomed,3, RoundingMode.HALF_UP);
        BigDecimal profitMarginPercent = divide.multiply(new BigDecimal("100"));
        // 3. 格式化小数位数（保留1位）
        BigDecimal formattedPercent = profitMarginPercent.setScale(1, RoundingMode.HALF_UP);
        // 4. 拼接%，转为字符串
        String yearNetProfit = formattedPercent + "%";


        YearMessageVo.setAnnualTotalExpenditure(Expenditure);
        YearMessageVo.setAnnualTotalIncome(TotalIncome);
        YearMessageVo.setAnnualNetProfit(NetProfit.toString());
        YearMessageVo.setTotalStudents(totalStudents);
        //todo 完成培训的学员
        YearMessageVo.setProfitMargin(yearNetProfit);
        return YearMessageVo;
    }


}
