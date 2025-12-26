package com.mashang.mashangdriving.mapper.student;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.vo.student.DrivingStudentDtlVo;
import org.apache.ibatis.annotations.Param;

public interface DrivingStudentMapper extends BaseMapper<DrivingStudent> {
    DrivingStudentDtlVo selectById(@Param("studentId") Long studentId);
}
