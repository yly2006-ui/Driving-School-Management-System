package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingLocation;
import com.mashang.mashangdriving.domain.vo.manager.DrivingLocationListVo;
import org.apache.ibatis.annotations.Param;

public interface DrivingLocationMapper extends BaseMapper<DrivingLocation> {

    // 分页查询所有地点（带省市县）
    Page<DrivingLocationListVo> query(@Param("page") Page<DrivingLocationListVo> page,
                                      @Param(Constants.WRAPPER) LambdaQueryWrapper<DrivingLocation> lambdaQueryWrapper);

}
