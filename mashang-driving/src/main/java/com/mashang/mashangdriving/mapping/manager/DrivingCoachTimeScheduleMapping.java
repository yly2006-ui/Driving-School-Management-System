package com.mashang.mashangdriving.mapping.manager;

import com.mashang.mashangdriving.domain.entity.DrivingCoachTimeSchedule;
import com.mashang.mashangdriving.domain.entity.DrivingInstructor;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingCoachTimeScheduleCreate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCoachTimeScheduleVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCoahTimeAndInstructorDtlVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DrivingCoachTimeScheduleMapping {

    DrivingCoachTimeScheduleMapping INSTANCE= Mappers.getMapper(DrivingCoachTimeScheduleMapping.class);

    List<DrivingCoachTimeScheduleVo> toListVo(List<DrivingCoachTimeSchedule> drivingCoachTimeSchedules);

    List<DrivingCoachTimeSchedule> toCreate(List<DrivingCoachTimeScheduleCreate> drivingCoachTimeScheduleCreates);

    DrivingCoahTimeAndInstructorDtlVo dtlVo(DrivingInstructor drivingInstructor);



}
