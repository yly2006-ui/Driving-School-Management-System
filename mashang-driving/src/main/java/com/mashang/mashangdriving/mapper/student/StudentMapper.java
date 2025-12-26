package com.mashang.mashangdriving.mapper.student;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.vo.student.StudentDataOverviewDtlVo;

public interface StudentMapper extends BaseMapper<DrivingStudent> {

    //根据userId查询学生实体
    StudentDataOverviewDtlVo student(Long userId);
}
