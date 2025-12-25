package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingLocation;
import com.mashang.mashangdriving.domain.vo.manager.DrivingLocationListVo;
import com.mashang.mashangdriving.mapper.manager.DrivingLocationMapper;
import com.mashang.mashangdriving.service.manager.IDrivingLocationService;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrivingLocationServiceImpl extends ServiceImpl<DrivingLocationMapper, DrivingLocation> implements IDrivingLocationService {
    @Autowired
    private DrivingLocationMapper drivingLocationMapper;
    @Autowired
    private ISysDictDataService dictDataService;

    @Override
    public Page<DrivingLocationListVo> query(Page<DrivingLocationListVo> page, LambdaQueryWrapper<DrivingLocation> wrapper) {
        Page<DrivingLocationListVo> query = drivingLocationMapper.query(page, wrapper);
        List<DrivingLocationListVo> records = query.getRecords();
        for (DrivingLocationListVo vo : records) {
            if (vo.getLocationTypeId() != null) {
                String typeName = dictDataService.selectDictLabel("place_type", vo.getLocationTypeId());
                vo.setLocationTypeName(typeName);
            }
            if (vo.getStatus()!=null){
                String s = dictDataService.selectDictLabel("place_status", vo.getStatus());
                vo.setStatusName(s);
            }
        }
        return query;
    }
}
