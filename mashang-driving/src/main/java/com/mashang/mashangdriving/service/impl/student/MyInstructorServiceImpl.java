package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.MyInstructor;
import com.mashang.mashangdriving.domain.vo.student.MyInstructorVo;
import com.mashang.mashangdriving.mapper.student.MyInstructorMapper;
import com.mashang.mashangdriving.service.student.IMyInstructorService;
import org.springframework.stereotype.Service;

@Service
public class MyInstructorServiceImpl extends ServiceImpl<MyInstructorMapper, MyInstructor> implements IMyInstructorService {
    @Override
    public MyInstructorVo selectMyInstructorById(Long studentId) {
        MyInstructorVo myInstructorVo = baseMapper.selectMyInstructorById(studentId);
        if (myInstructorVo == null) {
            throw new RuntimeException("数据不存在");
        }
        return myInstructorVo;
    }
}
