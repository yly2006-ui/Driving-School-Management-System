package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingInstructor;
import com.mashang.mashangdriving.domain.vo.student.AllInstructorListVo;

import java.util.List;

public interface IInstructorService extends IService<DrivingInstructor> {

    //查询所有在职教练
    List<AllInstructorListVo> allnstructorList();

    AllInstructorListVo myInstructor();

    //科目三教练
    AllInstructorListVo myThreeInstructor();
}
