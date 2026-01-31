package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingCar;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCarListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCarListVo1;
import com.mashang.mashangdriving.domain.vo.manager.DrivingPayRecordVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DrivingCarMapper  extends BaseMapper<DrivingCar> {

    Page<DrivingCarListVo> getDrivingCarByPage(Page<DrivingCarListVo> page);
    Page<DrivingPayRecordVo> getCarPay(@Param("carId")Long carId,Page<DrivingPayRecordVo> page);
    Map<String,Object> getCarStatistics();

}
