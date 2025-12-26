package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.vo.student.DrivingStudentDtlVo;
import com.mashang.mashangdriving.mapper.student.DrivingStudentMapper;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.service.student.IDrivingStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrivingStudentServiceImpl extends ServiceImpl<DrivingStudentMapper, DrivingStudent> implements IDrivingStudentService {
    @Autowired
    private DrivingStudentMapper drivingStudentMapper;
    @Override
    public DrivingStudentDtlVo selectById(Long studentId) {
        return  drivingStudentMapper.selectById(studentId);
    }
}
