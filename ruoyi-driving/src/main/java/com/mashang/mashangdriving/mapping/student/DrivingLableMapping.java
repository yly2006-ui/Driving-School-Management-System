package com.mashang.mashangdriving.mapping.student;

import com.mashang.mashangdriving.domain.entity.DrivingLable;
import com.mashang.mashangdriving.domain.param.student.create.DrivingLableCreate;
import com.mashang.mashangdriving.domain.param.student.update.DrivingLableUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DrivingLableMapping {
    DrivingLableMapping INSTANCE = Mappers.getMapper(DrivingLableMapping.class);

    DrivingLable toCreate(DrivingLableCreate drivingLableCreate);

    DrivingLable toUpdate(DrivingLableUpdate drivingLableUpdate);
}
