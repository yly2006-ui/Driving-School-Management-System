package com.mashang.mashangdriving.mapper.student;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.MyInstructor;
import com.mashang.mashangdriving.domain.vo.student.MyInstructorVo;
import org.apache.ibatis.annotations.Param;

public interface MyInstructorMapper extends BaseMapper<MyInstructor> {
    MyInstructorVo selectMyInstructorById(@Param("studentId") Long studentId);
}
