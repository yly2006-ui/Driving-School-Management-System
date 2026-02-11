package com.mashang.mashangdriving.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.MyInstructor;
import com.mashang.mashangdriving.domain.vo.student.MyInstructorVo;

public interface IMyInstructorService extends IService<MyInstructor> {
    MyInstructorVo selectMyInstructorById( Long studentId);
    int[][] createScheduleMatrixFromDB(Long studentId);


}
