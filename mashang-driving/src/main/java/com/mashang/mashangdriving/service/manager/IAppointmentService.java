package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingAppointment;
import com.mashang.mashangdriving.domain.param.manager.create.CreateStudentAppointment;
import com.mashang.mashangdriving.domain.vo.student.ContactInstructorVo;
import com.mashang.mashangdriving.domain.vo.student.MyAppointmentDtlVo;
import com.mashang.mashangdriving.domain.vo.student.StudentAppointmentVo;

import java.util.List;

public interface IAppointmentService extends IService<DrivingAppointment> {

    //昨日未处理预约
    int countYesterdayStatusOne();

    //今日未处理所有预约
    int countOnDayStatusOne();

    //学员端提交预约
    StudentAppointmentVo createAppointment(CreateStudentAppointment createStudentAppointment);

    //获取我的所有预约
    List<MyAppointmentDtlVo> myAllAppointment();

    //联系教练接口
    ContactInstructorVo getContactInstructor(Long appointmentId);
}
