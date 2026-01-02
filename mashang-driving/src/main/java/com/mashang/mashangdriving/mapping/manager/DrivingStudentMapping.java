package com.mashang.mashangdriving.mapping.manager;

import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface DrivingStudentMapping {

    DrivingStudentMapping INSTANCE = Mappers.getMapper(DrivingStudentMapping.class);

    List<DrivingStudentListVo> toDrivingStudentListVo(List<DrivingStudent> drivingStudent);

}
