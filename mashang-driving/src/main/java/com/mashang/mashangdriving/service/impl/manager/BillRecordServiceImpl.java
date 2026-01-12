package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;
import com.mashang.mashangdriving.domain.vo.student.BillRecordDtlVo;
import com.mashang.mashangdriving.domain.vo.student.BillRecordListVo;
import com.mashang.mashangdriving.mapper.manager.BillRecordMapper;
import com.mashang.mashangdriving.mapper.student.PayMapper;
import com.mashang.mashangdriving.service.manager.IBillRecordService;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class BillRecordServiceImpl extends ServiceImpl<BillRecordMapper, DrivingBillRecord> implements IBillRecordService {

    @Autowired
    private BillRecordMapper billRecordMapper;

    @Autowired
    private PayMapper payMapper;

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

    @Override
    public BillRecordListVo billRecordListVo() {

        BillRecordListVo billRecordListVo = new BillRecordListVo();
        billRecordListVo.setTotalPaymentAmount(billRecordMapper.paymentCount(SecurityUtils.getUserId()));
        billRecordListVo.setPaymentCount(billRecordMapper.paymentCount(SecurityUtils.getUserId()));
        billRecordListVo.setPayDtlVoList(payMapper.payListVo(SecurityUtils.getUserId()));

        return billRecordListVo;
    }

    @Override
    public BillRecordDtlVo paymentDtl(Long payId) {
        return payMapper.paymentDtl(payId);
    }
}
