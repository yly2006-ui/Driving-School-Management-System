package com.mashang.mashangdriving.mapper.student;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.DrivingPay;
import com.mashang.mashangdriving.domain.vo.student.BillRecordDtlVo;
import com.mashang.mashangdriving.domain.vo.student.PayDtlVo;

import java.util.List;

public interface PayMapper extends BaseMapper<DrivingPay> {

    //学员缴费记录集合
    List<PayDtlVo> payListVo(Long UserId);

    //缴费详情
    BillRecordDtlVo paymentDtl(Long payId);
}
