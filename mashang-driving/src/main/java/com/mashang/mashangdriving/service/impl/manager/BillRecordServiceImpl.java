package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;
import com.mashang.mashangdriving.mapper.manager.BillRecordMapper;
import com.mashang.mashangdriving.service.manager.IBillRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class BillRecordServiceImpl extends ServiceImpl<BillRecordMapper, DrivingBillRecord> implements IBillRecordService {

    @Autowired
    private BillRecordMapper billRecordMapper;

    /**
     * 查询上个月的总收入
     */
    @Override
    public double getLastMonthTotalIncome() {

        // 当前日期
        LocalDate now = LocalDate.now();

        // 上个月第一天 00:00:00
        LocalDateTime startTime = now
                .minusMonths(1)
                .withDayOfMonth(1)
                .atStartOfDay();

        // 本月第一天 00:00:00
        LocalDateTime endTime = now
                .withDayOfMonth(1)
                .atStartOfDay();

        // 直接数据库聚合
        return billRecordMapper.selectLastMonthTotalIncome(startTime, endTime);
    }

    @Override
    public double selectOnMonthTotalIncome() {
        return billRecordMapper.selectOnMonthTotalIncome();
    }
}
