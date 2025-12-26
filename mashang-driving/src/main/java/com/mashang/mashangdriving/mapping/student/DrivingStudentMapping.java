package com.mashang.mashangdriving.mapping.student;

import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.param.student.update.DrivingStudentUpdate;
import com.mashang.mashangdriving.mapping.manager.DrivingLocationMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DrivingStudentMapping {
    DrivingStudentMapping INSTANCE =Mappers.getMapper(DrivingStudentMapping.class);

    //修改个人信息
    DrivingStudent toUpdtae(DrivingStudentUpdate drivingStudentUpdate);

}
