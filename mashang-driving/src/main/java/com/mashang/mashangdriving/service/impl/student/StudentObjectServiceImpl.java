package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingStudentObject;
import com.mashang.mashangdriving.mapper.student.StudentObjectMapper;
import com.mashang.mashangdriving.service.student.IStudentObjectService;
import org.springframework.stereotype.Service;

@Service
public class StudentObjectServiceImpl extends ServiceImpl<StudentObjectMapper, DrivingStudentObject> implements IStudentObjectService {
}
