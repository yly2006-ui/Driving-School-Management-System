package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingInstructorStudent;
import com.mashang.mashangdriving.mapper.student.InstructorStudentMapper;
import com.mashang.mashangdriving.service.student.IInstructorStudentService;
import org.springframework.stereotype.Service;

@Service
public class InstructorStudentServiceImpl extends ServiceImpl<InstructorStudentMapper, DrivingInstructorStudent> implements IInstructorStudentService {
}
