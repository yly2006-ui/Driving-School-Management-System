package com.mashang.mashangdriving.mapping.student;

import com.mashang.mashangdriving.domain.entity.DrivingAppointment;
import com.mashang.mashangdriving.domain.param.manager.create.CreateStudentAppointment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreateAppointmentMapping {

    CreateAppointmentMapping INSTANCE = Mappers.getMapper(CreateAppointmentMapping.class);

    //添加转实体
    DrivingAppointment toEntity(CreateStudentAppointment createStudentAppointment);
}
