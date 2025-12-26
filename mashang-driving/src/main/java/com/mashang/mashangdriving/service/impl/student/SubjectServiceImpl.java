package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingSubject;
import com.mashang.mashangdriving.mapper.manager.SubjectMapper;
import com.mashang.mashangdriving.service.student.ISubjectService;
import org.springframework.stereotype.Service;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, DrivingSubject> implements ISubjectService {
}
