package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;
import com.mashang.mashangdriving.domain.vo.student.BillRecordDtlVo;
import com.mashang.mashangdriving.domain.vo.student.BillRecordListVo;

public interface IBillRecordService extends IService<DrivingBillRecord> {

    double getLastMonthTotalIncome();
    double selectOnMonthTotalIncome();

    BillRecordListVo billRecordListVo();

    BillRecordDtlVo paymentDtl(Long payId);
}
