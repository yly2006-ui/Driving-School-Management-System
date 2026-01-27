package com.mashang.mashangdriving.mapping.manager;

import com.mashang.mashangdriving.domain.entity.Area;
import com.mashang.mashangdriving.domain.entity.DrivingChapter;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingChapterCreate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingChapterUpdate;
import com.mashang.mashangdriving.domain.vo.manager.AreaListVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AreaMapping {
    AreaMapping INSTANCE= Mappers.getMapper(AreaMapping.class);

    //查询转响应
    List<AreaListVO> tolist(List<Area>areaList);

}
