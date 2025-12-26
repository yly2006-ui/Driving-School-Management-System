package com.mashang.mashangdriving.mapping.manager;

import com.mashang.mashangdriving.domain.entity.DrivingNotice;
import com.mashang.mashangdriving.domain.vo.manager.DataOverviewNoticeDtlVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NoticeMapping {

    NoticeMapping INSTANCE = Mappers.getMapper(NoticeMapping.class);

    //实体集合转响应集合
    List<DataOverviewNoticeDtlVo> toDataOverviewNoticeDtlVo(List<DrivingNotice> listNotice);
}
