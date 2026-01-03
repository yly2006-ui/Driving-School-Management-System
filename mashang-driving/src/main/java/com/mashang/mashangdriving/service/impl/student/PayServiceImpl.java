package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingPay;
import com.mashang.mashangdriving.mapper.student.PayMapper;
import com.mashang.mashangdriving.service.student.IPayService;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl extends ServiceImpl<PayMapper, DrivingPay> implements IPayService {
}
