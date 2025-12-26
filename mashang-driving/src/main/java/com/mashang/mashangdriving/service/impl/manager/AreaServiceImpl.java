package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.Area;
import com.mashang.mashangdriving.domain.vo.manager.AreaListVO;
import com.mashang.mashangdriving.mapper.manager.AreaMapper;
import com.mashang.mashangdriving.service.manager.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {
    @Autowired
    private AreaMapper areaMapper;
    @Override
    public List<AreaListVO> select() {
        return areaMapper.select();
    }
}
