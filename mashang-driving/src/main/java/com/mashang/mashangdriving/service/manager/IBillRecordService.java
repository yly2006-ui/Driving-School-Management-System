package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;

public interface IBillRecordService extends IService<DrivingBillRecord> {

    double getLastMonthTotalIncome();
    double selectOnMonthTotalIncome();
}
