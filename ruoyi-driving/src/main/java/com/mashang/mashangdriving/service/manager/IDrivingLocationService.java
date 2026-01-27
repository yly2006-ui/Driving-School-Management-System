package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingLocation;
import com.mashang.mashangdriving.domain.vo.manager.DrivingLocationListVo;

public interface IDrivingLocationService extends IService<DrivingLocation> {

    Page<DrivingLocationListVo> query(Page<DrivingLocationListVo> page, LambdaQueryWrapper<DrivingLocation> lambdaQueryWrapper);

}
