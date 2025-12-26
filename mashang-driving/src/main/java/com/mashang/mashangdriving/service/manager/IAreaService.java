package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.Area;
import com.mashang.mashangdriving.domain.vo.manager.AreaListVO;

import java.util.List;

public interface IAreaService extends IService<Area> {
    List<AreaListVO> select();
}
