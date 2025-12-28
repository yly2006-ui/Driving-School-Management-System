package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingAppointment;
import com.mashang.mashangdriving.domain.entity.DrivingInstructor;
import com.mashang.mashangdriving.domain.entity.DrivingInstructorStudent;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.vo.student.AllInstructorListVo;
import com.mashang.mashangdriving.mapper.manager.InstructorMapper;
import com.mashang.mashangdriving.mapper.student.InstructorStudentMapper;
import com.mashang.mashangdriving.mapper.student.StudentMapper;
import com.mashang.mashangdriving.mapping.manager.InstructorMapping;
import com.mashang.mashangdriving.service.manager.IInstructorService;
import com.ruoyi.common.constant.InstructorConstants;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InstructorServiceImpl extends ServiceImpl<InstructorMapper, DrivingInstructor> implements IInstructorService {

    @Autowired
    private InstructorMapper instructorMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private InstructorStudentMapper instructorStudentMapper;

    @Override
    public List<AllInstructorListVo> allnstructorList() {

        return instructorMapper.allnstructorList();
    }

    @Override
    public AllInstructorListVo myInstructor() {

        DrivingStudent student = studentMapper.selectOne(
                Wrappers.<DrivingStudent>lambdaQuery()
                        .eq(DrivingStudent::getUserId, SecurityUtils.getUserId())
                        .eq(DrivingStudent::getDelFlag, "0")
        );
        DrivingInstructorStudent drivingInstructorStudent = instructorStudentMapper.selectById(student.getStudentId());

        return instructorMapper.myInstructor(drivingInstructorStudent.getInstructorId());
    }

    @Override
    public AllInstructorListVo myThreeInstructor() {

        DrivingStudent student = studentMapper.selectOne(
                Wrappers.<DrivingStudent>lambdaQuery()
                        .eq(DrivingStudent::getUserId, SecurityUtils.getUserId())
                        .eq(DrivingStudent::getDelFlag, "0")
        );

        DrivingInstructorStudent drivingInstructorStudent = instructorStudentMapper.selectById(student.getStudentId());

        return instructorMapper.myInstructor(drivingInstructorStudent.getInstructorId());
    }
}
