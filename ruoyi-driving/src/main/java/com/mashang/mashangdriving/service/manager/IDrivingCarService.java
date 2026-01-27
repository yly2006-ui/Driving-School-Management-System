package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingCar;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingCarCreate;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingCarQuery;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingCarUpdate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCarListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCarListVo1;
import com.mashang.mashangdriving.domain.vo.manager.DrivingPayRecordVo;

import java.util.List;
import java.util.Map;

public interface IDrivingCarService extends IService<DrivingCar> {
    Page<DrivingCarListVo> getDrivingCarByPage(Page<DrivingCarListVo> page);
    List<DrivingCar> selectList(DrivingCarQuery drivingCarQuery);
    DrivingCarListVo insertCar(DrivingCarCreate drivingCarCreate);
    DrivingCarListVo updateCar(DrivingCarUpdate drivingCarUpdate);
    Page<DrivingPayRecordVo> getCarPay(Long carId,Page<DrivingPayRecordVo> page);
    Map<String,Object> getCarStats();
    DrivingCarListVo1 getOneCar(Long carId);


}
