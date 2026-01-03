package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingLable;
import com.mashang.mashangdriving.mapper.student.DrivingLableMapper;
import com.mashang.mashangdriving.service.student.IDrivingLableService;
import org.springframework.stereotype.Service;

@Service
public class DrivingLableServiceImpl extends ServiceImpl<DrivingLableMapper, DrivingLable> implements IDrivingLableService {
}
