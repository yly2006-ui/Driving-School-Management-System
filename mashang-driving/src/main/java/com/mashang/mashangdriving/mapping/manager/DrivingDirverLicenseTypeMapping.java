package com.mashang.mashangdriving.mapping.manager;

import com.mashang.mashangdriving.domain.entity.DrivingDriverLicenseType;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingDriverLicenseTypeCreate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingDriverLicenseTypeListVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DrivingDirverLicenseTypeMapping {

    DrivingDirverLicenseTypeMapping INSTANCE = Mappers.getMapper(DrivingDirverLicenseTypeMapping.class);

    //分页查询驾照类型
    List<DrivingDriverLicenseTypeListVo> toListVo(List<DrivingDriverLicenseType> drivingDriverLicenseTypes);

    //新增驾照类型
    DrivingDriverLicenseType toCreate (DrivingDriverLicenseTypeCreate drivingDriverLicenseTypeCreate);

}
