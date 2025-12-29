package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.*;
import com.mashang.mashangdriving.domain.vo.student.AllInstructorListVo;
import com.mashang.mashangdriving.mapper.manager.InstructorMapper;
import com.mashang.mashangdriving.mapper.student.InstructorStudentMapper;
import com.mashang.mashangdriving.mapper.student.StudentMapper;
import com.mashang.mashangdriving.mapper.student.StudentObjectMapper;
import com.mashang.mashangdriving.mapping.manager.InstructorMapping;
import com.mashang.mashangdriving.service.manager.IInstructorService;
import com.ruoyi.common.constant.InstructorConstants;
import com.ruoyi.common.constant.ObjectConstants;
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



    @Autowired
    private StudentObjectMapper studentObjectMapper;

    @Override
    public List<AllInstructorListVo> allnstructorList() {

        return instructorMapper.allnstructorList();
    }

    @Override
    public AllInstructorListVo myInstructor() {

        LambdaQueryWrapper<DrivingStudent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DrivingStudent::getUserId,SecurityUtils.getUserId());
        DrivingStudent drivingStudent = studentMapper.selectOne(wrapper);

        LambdaQueryWrapper<DrivingInstructorStudent> allWrapper = new LambdaQueryWrapper<>();
        allWrapper.eq(DrivingInstructorStudent::getSubjectId, ObjectConstants.TWO_OBJECT);
        allWrapper.eq(DrivingInstructorStudent::getStudentId,drivingStudent.getStudentId());
        List<DrivingInstructorStudent> drivingInstructorStudents = instructorStudentMapper.selectList(allWrapper);

        Long instructorId = drivingInstructorStudents.stream()
                .map(DrivingInstructorStudent::getInstructorId)
                .distinct()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("未找到教练"));
        return instructorMapper.myInstructor(instructorId);
    }

    @Override
    public AllInstructorListVo myThreeInstructor() {

        LambdaQueryWrapper<DrivingStudent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DrivingStudent::getUserId,SecurityUtils.getUserId());
        DrivingStudent drivingStudent = studentMapper.selectOne(wrapper);

        LambdaQueryWrapper<DrivingInstructorStudent> allWrapper = new LambdaQueryWrapper<>();
        allWrapper.eq(DrivingInstructorStudent::getSubjectId, ObjectConstants.THREE_OBJECT);
        allWrapper.eq(DrivingInstructorStudent::getStudentId,drivingStudent.getStudentId());
        List<DrivingInstructorStudent> drivingInstructorStudents = instructorStudentMapper.selectList(allWrapper);

        Long instructorId = drivingInstructorStudents.stream()
                .map(DrivingInstructorStudent::getInstructorId)
                .distinct()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("未找到教练"));
        return instructorMapper.myInstructor(instructorId);
    }
}
