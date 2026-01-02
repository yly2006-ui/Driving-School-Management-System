package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingAppointment;
import com.mashang.mashangdriving.domain.entity.DrivingSubject;
import com.mashang.mashangdriving.domain.vo.student.AllSubjectVo;
import com.mashang.mashangdriving.mapper.manager.SubjectMapper;
import com.mashang.mashangdriving.mapping.student.SubjectMapping;
import com.mashang.mashangdriving.service.student.ISubjectService;
import com.ruoyi.common.constant.AppointmentConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, DrivingSubject> implements ISubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public List<AllSubjectVo> allSubject() {

        LambdaQueryWrapper<DrivingSubject> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DrivingSubject::getDelFlag,"0");
        List<DrivingSubject> drivingSubjects = subjectMapper.selectList(wrapper);

        return SubjectMapping.INSTANCE.toDtl(drivingSubjects);
    }
}
