package com.mashang.mashangdriving.mapping.manager;

import com.mashang.mashangdriving.domain.entity.DrivingCourse;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingCourseUpdate;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingCourseCreate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCourseListVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface DrivingCourseMapping {
    DrivingCourseMapping INSTANCE = Mappers.getMapper(DrivingCourseMapping.class);

    List<DrivingCourseListVo> toList(List<DrivingCourse> drivingCourseList);

    //新增课程
    DrivingCourse toCreate(DrivingCourseCreate drivingCourseCreate);

    //修改课程
    DrivingCourse toUpdate(DrivingCourseUpdate drivingCourseUpdate);
}
