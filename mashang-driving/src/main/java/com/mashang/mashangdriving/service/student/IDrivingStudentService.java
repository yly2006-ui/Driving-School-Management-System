package com.mashang.mashangdriving.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.vo.student.DrivingStudentDtlVo;
import org.apache.ibatis.annotations.Param;

public interface IDrivingStudentService extends IService<DrivingStudent> {
    DrivingStudentDtlVo selectById(@Param("studentId") Long studentId);
}
