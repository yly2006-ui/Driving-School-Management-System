package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingAppointment;
import com.mashang.mashangdriving.domain.param.manager.create.CreateStudentAppointment;
import com.mashang.mashangdriving.domain.vo.student.StudentAppointmentVo;

public interface IAppointmentService extends IService<DrivingAppointment> {

    //昨日未处理预约
    int countYesterdayStatusOne();

    //今日未处理所有预约
    int countOnDayStatusOne();

    StudentAppointmentVo createAppointment(CreateStudentAppointment createStudentAppointment);
}
