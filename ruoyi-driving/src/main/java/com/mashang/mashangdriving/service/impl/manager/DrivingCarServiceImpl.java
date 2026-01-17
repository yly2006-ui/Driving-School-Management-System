package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.Exception.BusinessException;
import com.mashang.mashangdriving.domain.entity.DrivingCar;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingCarCreate;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingCarQuery;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingCarUpdate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCarListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingPayRecordVo;
import com.mashang.mashangdriving.mapper.manager.DrivingCarMapper;
import com.mashang.mashangdriving.service.manager.IDrivingCarService;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DrivingCarServiceImpl extends ServiceImpl<DrivingCarMapper,DrivingCar> implements IDrivingCarService {

    @Override
    public Page<DrivingCarListVo> getDrivingCarByPage(Page<DrivingCarListVo> page) {
        return baseMapper.getDrivingCarByPage(page);
    }

    @Override
    public List<DrivingCar> selectList(DrivingCarQuery drivingCarQuery) {
        LambdaQueryWrapper<DrivingCar> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(drivingCarQuery.getPlateNumber())) {
            wrapper.eq(DrivingCar::getPlateNumber, drivingCarQuery.getPlateNumber());
        }

        if (StringUtils.hasText(drivingCarQuery.getCarName())) {
            wrapper.eq(DrivingCar::getCarName, drivingCarQuery.getCarName());
        }

        return baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public DrivingCarListVo insertCar(DrivingCarCreate drivingCarCreate) {
        if (drivingCarCreate == null) {
            throw new RuntimeException("参数不能为空");
        }
        if (drivingCarCreate.getInstructorId() == null) {
            throw new RuntimeException("教练ID不能为空");
        }
        if (drivingCarCreate.getCarName() == null || drivingCarCreate.getCarName().trim().isEmpty()) {
            throw new RuntimeException("车辆名称不能为空");
        }
        if (drivingCarCreate.getPlateNumber() == null || drivingCarCreate.getPlateNumber().trim().isEmpty()) {
            throw new RuntimeException("车牌号不能为空");
        }
        if (drivingCarCreate.getCarType() == null || drivingCarCreate.getCarType().trim().isEmpty()) {
            throw new RuntimeException("车辆类型不能为空");
        }
        DrivingCar car = getDrivingCar(drivingCarCreate);
        int i = baseMapper.insert(car);
        if (i < 0) {
            throw new RuntimeException("保存失败");
        }

        return getDrivingCarListVo(car);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public DrivingCarListVo updateCar(DrivingCarUpdate drivingCarUpdate) {
        LambdaUpdateWrapper<DrivingCar> wrapper = new LambdaUpdateWrapper<>();
        if(drivingCarUpdate.getCarId() != null){
            wrapper.eq(DrivingCar::getCarId, drivingCarUpdate.getCarId());
        }else {
            throw new RuntimeException("请输入汽车ID");
        }
        if (StringUtils.hasText(drivingCarUpdate.getPlateNumber())) {
            wrapper.set(DrivingCar::getPlateNumber, drivingCarUpdate.getPlateNumber());
        }
        if (StringUtils.hasText(drivingCarUpdate.getCarName())) {
            wrapper.set(DrivingCar::getCarName, drivingCarUpdate.getCarName());
        }
        if (StringUtils.hasText(drivingCarUpdate.getCarType())) {
            wrapper.set(DrivingCar::getCarType, drivingCarUpdate.getCarType());
        }
        if (StringUtils.hasText(drivingCarUpdate.getCarColor())){
            wrapper.set(DrivingCar::getCarColor, drivingCarUpdate.getCarColor());
        }
        if(StringUtils.hasText(drivingCarUpdate.getCarBrand())){
            wrapper.set(DrivingCar::getCarBrand, drivingCarUpdate.getCarBrand());
        }
        if(StringUtils.hasText(drivingCarUpdate.getEin())){
            wrapper.set(DrivingCar::getEin, drivingCarUpdate.getEin());
        }
        if(StringUtils.hasText(drivingCarUpdate.getVin())){
            wrapper.set(DrivingCar::getVin, drivingCarUpdate.getVin());
        }
        if(StringUtils.hasText(drivingCarUpdate.getTransmissionType())){
            wrapper.set(DrivingCar::getTransmissionType, drivingCarUpdate.getTransmissionType());
        }
        if(StringUtils.hasText(String.valueOf(drivingCarUpdate.getMotExpires()))){
            wrapper.set(DrivingCar::getMotExpires, drivingCarUpdate.getMotExpires());
        }
        if(StringUtils.hasText(String.valueOf(drivingCarUpdate.getFullPersion()))){
            wrapper.set(DrivingCar::getFullPersion, drivingCarUpdate.getFullPersion());
        }
        int update = baseMapper.update(null, wrapper);
        if (update < 0) {
            throw new RuntimeException("修改失败");
        }
        DrivingCar car = baseMapper.selectById(drivingCarUpdate.getCarId());
        return getDrivingCarListVo(car);
    }

    @Override
    public List<DrivingPayRecordVo> getCarPay(Long carId) {
        List<DrivingPayRecordVo> carPay = baseMapper.getCarPay(carId);
        if (carPay == null) {
            throw new RuntimeException("支付记录不存在");
        }
        return carPay;
    }

    private static DrivingCar getDrivingCar(DrivingCarCreate drivingCarCreate) {
        DrivingCar car = new DrivingCar();
        car.setCarName(drivingCarCreate.getCarName());
        car.setPlateNumber(drivingCarCreate.getPlateNumber());
        car.setCarType(drivingCarCreate.getCarType());
        car.setInstructorId(drivingCarCreate.getInstructorId());
        car.setStatus("0");
        car.setDelFlag("0");
        car.setBuyDate(new Date());
        if (drivingCarCreate.getCarColor() != null) {
            car.setCarColor(drivingCarCreate.getCarColor());
        }
        if (drivingCarCreate.getCarBrand() != null) {
            car.setCarBrand(drivingCarCreate.getCarBrand());
        }
        if (drivingCarCreate.getTransmissionType() != null) {
            car.setTransmissionType(drivingCarCreate.getTransmissionType());
        }
        if (drivingCarCreate.getEin() != null) {
            car.setEin(drivingCarCreate.getEin());
        }
        if (drivingCarCreate.getVin() != null) {
            car.setVin(drivingCarCreate.getVin());
        }
        if (drivingCarCreate.getMotExpires() != null) {
            car.setMotExpires(drivingCarCreate.getMotExpires());
        }
        if (drivingCarCreate.getFullPersion() != null) {
            car.setFullPersion(drivingCarCreate.getFullPersion());
        }
        return car;
    }

    private static DrivingCarListVo getDrivingCarListVo(DrivingCar drivingCar) {
        DrivingCarListVo drivingCarListVo = new DrivingCarListVo();
        drivingCarListVo.setCarColor(String.valueOf(drivingCar.getCarColor()));
        drivingCarListVo.setCarBrand(String.valueOf(drivingCar.getCarBrand()));
        drivingCarListVo.setCarType(String.valueOf(drivingCar.getCarType()));
        drivingCarListVo.setCarName(drivingCar.getCarName());
        drivingCarListVo.setPlateNumber(drivingCar.getPlateNumber());
        drivingCarListVo.setInstructorId(drivingCar.getInstructorId());
        drivingCarListVo.setEin(drivingCar.getEin());
        drivingCarListVo.setTransmissionType(drivingCar.getTransmissionType());
        drivingCarListVo.setVin(drivingCar.getVin());
        drivingCarListVo.setFullPersion(drivingCar.getFullPersion());
        drivingCarListVo.setMotExpires(drivingCar.getMotExpires());
        drivingCarListVo.setBuyDate(drivingCar.getBuyDate());
        drivingCarListVo.setStatus(drivingCar.getStatus());
        drivingCarListVo.setDelFlag(drivingCar.getDelFlag());
        drivingCarListVo.setCarId(drivingCar.getCarId());
        return drivingCarListVo;
    }


}
