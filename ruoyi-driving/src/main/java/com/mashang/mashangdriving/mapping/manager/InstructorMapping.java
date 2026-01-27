package com.mashang.mashangdriving.mapping.manager;

import com.mashang.mashangdriving.domain.entity.DrivingInstructor;
import com.mashang.mashangdriving.domain.vo.manager.ReservationSlot;
import com.mashang.mashangdriving.domain.vo.student.AllInstructorListVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface InstructorMapping {

    InstructorMapping INSTANCE = Mappers.getMapper(InstructorMapping.class);

    // ① 单对象
    AllInstructorListVo toListVo(DrivingInstructor entity);
    //教练集合实体转响应集合
    List<AllInstructorListVo> toList(List<DrivingInstructor> instructorList);

    //实体转可预约响应
    ReservationSlot toSlot(DrivingInstructor instructor);







}
