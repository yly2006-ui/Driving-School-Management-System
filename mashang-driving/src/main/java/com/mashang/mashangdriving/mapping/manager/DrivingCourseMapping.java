package com.mashang.mashangdriving.mapping.manager;

import com.mashang.mashangdriving.domain.entity.DrivingCourse;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCourseListVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface DrivingCourseMapping {
    DrivingCourseMapping INSTANCE = Mappers.getMapper(DrivingCourseMapping.class);

    List<DrivingCourseListVo> toList(List<DrivingCourse> drivingCourseList);
}
