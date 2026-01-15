package com.mashang.mashangdriving.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.MyInstructor;
import com.mashang.mashangdriving.domain.vo.student.MyInstructorVo;
import org.apache.ibatis.annotations.Param;

public interface IMyInstructorService extends IService<MyInstructor> {
    MyInstructorVo selectMyInstructorById( Long studentId);

}
