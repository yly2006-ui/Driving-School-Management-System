package com.mashang.mashangdriving.mapping.manager;

import com.mashang.mashangdriving.domain.entity.DrivingSection;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingSectionCreate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingSectionUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface DrivingSectionMapping {

    DrivingSectionMapping INSTANCE =Mappers.getMapper(DrivingSectionMapping.class);

    //新增小节
    DrivingSection toCreate (DrivingSectionCreate drivingSectionCreate);

    //修改小节
    DrivingSection toUpdate (DrivingSectionUpdate drivingSectionUpdate);


}


