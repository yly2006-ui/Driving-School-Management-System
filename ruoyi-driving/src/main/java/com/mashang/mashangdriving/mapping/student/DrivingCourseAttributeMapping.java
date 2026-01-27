package com.mashang.mashangdriving.mapping.student;

import com.mashang.mashangdriving.domain.entity.DrivingCourseAttribute;
import com.mashang.mashangdriving.domain.param.student.create.DrivingCourseAttributeCreate;
import com.mashang.mashangdriving.domain.param.student.update.DrivingCourseAttributeUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface DrivingCourseAttributeMapping {
    DrivingCourseAttributeMapping INSTANCE = Mappers.getMapper(DrivingCourseAttributeMapping.class);

    DrivingCourseAttribute toCreate(DrivingCourseAttributeCreate drivingCourseAttributeCreate);

    DrivingCourseAttribute toUpdate(DrivingCourseAttributeUpdate drivingCourseAttributeUpdate);
}
