package com.mashang.mashangdriving.mapping.student;

import com.mashang.mashangdriving.domain.entity.DrivingSubject;
import com.mashang.mashangdriving.domain.vo.student.AllSubjectVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectMapping {

    SubjectMapping INSTANCE = Mappers.getMapper(SubjectMapping.class);

    // ① 单对象映射（MapStruct 会自动按字段名映射）
    AllSubjectVo toVo(DrivingSubject entity);

    List<AllSubjectVo> toDtl(List<DrivingSubject> subjects);
}
