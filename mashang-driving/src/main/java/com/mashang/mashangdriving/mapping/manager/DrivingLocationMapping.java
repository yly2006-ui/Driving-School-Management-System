package com.mashang.mashangdriving.mapping.manager;

import com.mashang.mashangdriving.domain.entity.DrivingLocation;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingLocationCreate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingLocationUpdate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingLocationDtlVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DrivingLocationMapping {
    DrivingLocationMapping INSTANCE =Mappers.getMapper(DrivingLocationMapping.class);

    //新增地点
    DrivingLocation toCreate(DrivingLocationCreate drivingLocationCreate);

    //修改地点
    DrivingLocation toUpdate(DrivingLocationUpdate drivingLocationUpdate);

    //查询地点详情
    DrivingLocationDtlVo toDtl(DrivingLocation drivingLocation);
}
