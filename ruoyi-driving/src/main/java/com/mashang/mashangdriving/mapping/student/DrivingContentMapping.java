package com.mashang.mashangdriving.mapping.student;

import com.mashang.mashangdriving.domain.entity.DrivingContent;
import com.mashang.mashangdriving.domain.param.student.create.DrivingContentCreate;
import com.mashang.mashangdriving.domain.param.student.update.DrivingContentUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DrivingContentMapping {
    DrivingContentMapping INSTANCE = Mappers.getMapper(DrivingContentMapping.class);

    DrivingContent toCreate(DrivingContentCreate drivingContentCreate);

    DrivingContent toUpdate(DrivingContentUpdate drivingContentUpdate);
}
