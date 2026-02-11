package com.mashang.mashangdriving.mapping.student;

import com.mashang.mashangdriving.domain.entity.DrivingCourseAttribute;
import com.mashang.mashangdriving.domain.param.student.create.DrivingCourseAttributeCreate;
import com.mashang.mashangdriving.domain.param.student.update.DrivingCourseAttributeUpdate;
import com.mashang.mashangdriving.domain.vo.student.DrivingCourseStudentListVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DrivingCourseAttributeMapping {
    DrivingCourseAttributeMapping INSTANCE = Mappers.getMapper(DrivingCourseAttributeMapping.class);

    List<DrivingCourseStudentListVo> toList(List<DrivingCourseAttribute> drivingCourseAttributes);

    DrivingCourseAttribute toCreate(DrivingCourseAttributeCreate drivingCourseAttributeCreate);

    DrivingCourseAttribute toUpdate(DrivingCourseAttributeUpdate drivingCourseAttributeUpdate);
}
