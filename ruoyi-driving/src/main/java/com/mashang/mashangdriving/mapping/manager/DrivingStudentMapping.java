package com.mashang.mashangdriving.mapping.manager;

import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface DrivingStudentMapping {

    DrivingStudentMapping INSTANCE = Mappers.getMapper(DrivingStudentMapping.class);

    // ① 单对象映射（MapStruct 会自动按字段名一一对应）
    DrivingStudentListVo toListVo(DrivingStudent entity);

    List<DrivingStudentListVo> toDrivingStudentListVo(List<DrivingStudent> drivingStudent);

}
