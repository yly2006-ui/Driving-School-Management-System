package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.DrivingInstructor;
import com.mashang.mashangdriving.domain.entity.DrivingInstructorStudent;
import com.mashang.mashangdriving.domain.vo.student.AllInstructorListVo;

import java.util.List;

public interface InstructorMapper extends BaseMapper<DrivingInstructor> {

    AllInstructorListVo myInstructor(Long instructorId);

    List<AllInstructorListVo> allnstructorList();
}
