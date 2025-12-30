package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingCar;
import com.mashang.mashangdriving.mapper.student.CarMapper;
import com.mashang.mashangdriving.service.student.ICarService;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, DrivingCar
        > implements ICarService {
}
