package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface BillRecordMapper extends BaseMapper<DrivingBillRecord> {

    /**
     * 查询上个月总收入
     */
    double selectLastMonthTotalIncome(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    /**
     * 查询这个月总收入
     */
    double selectOnMonthTotalIncome();

    /**
     * 查询个人缴费总金额
     */
    double paymentRecord(Long userId);

    //查询缴费次数
    int paymentCount(Long userId);
}
