package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingInstructor;
import com.mashang.mashangdriving.mapper.manager.DrivingInstructorMapper;
import com.mashang.mashangdriving.service.manager.IDrivingInstructorService;
import org.springframework.stereotype.Service;

@Service
public class DrivingInstructorServiceImpl extends ServiceImpl<DrivingInstructorMapper, DrivingInstructor> implements IDrivingInstructorService {
}
