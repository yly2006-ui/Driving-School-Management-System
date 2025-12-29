package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.DrivingAppointment;
import com.mashang.mashangdriving.domain.param.manager.create.CreateStudentAppointment;
import com.mashang.mashangdriving.domain.vo.student.StudentAppointmentVo;

public interface AppointmentMapper extends BaseMapper<DrivingAppointment> {

    StudentAppointmentVo appointmento();
}
