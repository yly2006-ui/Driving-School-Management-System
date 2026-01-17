package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.Area;
import com.mashang.mashangdriving.domain.vo.manager.AreaListVO;

import java.util.List;

public interface AreaMapper extends BaseMapper<Area> {
    List<AreaListVO> select();
}
