package com.mashang.mashangdriving.mapping.manager;

import com.mashang.mashangdriving.domain.entity.DrivingChapter;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingChapterCreate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingChapterUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface DrivingChapterMapping {
    DrivingChapterMapping INSTANCE= Mappers.getMapper(DrivingChapterMapping.class);

    //新增章
    DrivingChapter toCreate(DrivingChapterCreate drivingChapterCreate);

    //修改章
    DrivingChapter toUpdate(DrivingChapterUpdate drivingChapterUpdate);

}
