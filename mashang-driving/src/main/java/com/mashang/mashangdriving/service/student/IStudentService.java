package com.mashang.mashangdriving.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.vo.student.StudentDataOverviewDtlVo;

public interface IStudentService extends IService<DrivingStudent> {

    //上个月总学生
    int lastMonthStudentMount();

    //上个月在学学员
    int countLastMonthActiveStudent();

    //这个月所有在学学员
    int countOnMonthActiveStudent();

    //根据登陆信息查询该学员
    StudentDataOverviewDtlVo student();
}
