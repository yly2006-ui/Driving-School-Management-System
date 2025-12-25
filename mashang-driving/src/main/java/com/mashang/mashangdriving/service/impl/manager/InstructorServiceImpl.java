package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingInstructor;
import com.mashang.mashangdriving.mapper.manager.InstructorMapper;
import com.mashang.mashangdriving.service.manager.IInstructorService;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImpl extends ServiceImpl<InstructorMapper, DrivingInstructor> implements IInstructorService {
}
