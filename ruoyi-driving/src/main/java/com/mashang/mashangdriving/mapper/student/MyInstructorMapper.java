package com.mashang.mashangdriving.mapper.student;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.MyInstructor;
import com.mashang.mashangdriving.domain.vo.student.MyInstructorVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MyInstructorMapper extends BaseMapper<MyInstructor> {
    MyInstructorVo selectMyInstructorById(@Param("studentId") Long studentId);
    List<Map<String,Object>> findWeeklyScheduleByStudentId(@Param("studentId")Long studentId);


}
