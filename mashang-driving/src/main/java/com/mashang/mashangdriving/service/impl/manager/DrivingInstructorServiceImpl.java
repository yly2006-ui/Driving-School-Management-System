package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.query.MPJLambdaQueryWrapper;
import com.mashang.mashangdriving.domain.entity.DrivingCar;
import com.mashang.mashangdriving.domain.entity.DrivingInstructor;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingInstructorCreate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingInstructorUpdate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingInstructorListVo;
import com.mashang.mashangdriving.mapper.manager.DrivingCarMapper;
import com.mashang.mashangdriving.mapper.manager.DrivingInstructorMapper;
import com.mashang.mashangdriving.service.manager.IDrivingInstructorService;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class DrivingInstructorServiceImpl extends ServiceImpl<DrivingInstructorMapper, DrivingInstructor> implements IDrivingInstructorService {
    @Autowired
    private DrivingCarMapper drivingCarMapper;

    @Override
    public Page<DrivingInstructorListVo> getList(Page<DrivingInstructorListVo> page) {
        return baseMapper.getList(page);
    }

    @Override
    public DrivingInstructorListVo getByName(String name) {
        return baseMapper.getByName(name);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DrivingInstructorListVo insert(DrivingInstructorCreate dto) {
        DrivingInstructor drivingInstructor = new DrivingInstructor();
        drivingInstructor.setInstructorName(dto.getInstructorName());
        drivingInstructor.setIdNumber(dto.getIdNumber());
        drivingInstructor.setPhone(dto.getPhone());
        if(StringUtils.isNotBlank(dto.getCertificate())){
            drivingInstructor.setCertificate(dto.getCertificate());
        }
        if(StringUtils.isNotBlank(dto.getGoodSubject())){
            drivingInstructor.setGoodSubject(dto.getGoodSubject());
        }
        if(StringUtils.isNotBlank(dto.getTeachingYears())){
            drivingInstructor.setTeachingYears(dto.getTeachingYears());
        }
        drivingInstructor.setEntryDate(new Date());
        drivingInstructor.setInstructorNo("JL"+drivingInstructor.getEntryDate().getTime());
        drivingInstructor.setDelFlag("0");
        drivingInstructor.setStatus("0");
        int i = baseMapper.insert(drivingInstructor);
        if (i<=0){
            throw new RuntimeException("保存失败");
        }
        if (drivingInstructor.getInstructorId()!=null){
            drivingInstructor.setUserId(Long.valueOf("100"+drivingInstructor.getInstructorId()));
            baseMapper.updateById(drivingInstructor);
        }else  {
            drivingInstructor.setUserId(null);
        }

        return getDrivingInstructorVo(drivingInstructor);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DrivingInstructorListVo update(DrivingInstructorUpdate drivingInstructorUpdate) {
        if (drivingInstructorUpdate.getInstructorId()==null){
            throw new RuntimeException("ID不能为空");
        }

        LambdaUpdateWrapper<DrivingInstructor> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(DrivingInstructor::getInstructorId,drivingInstructorUpdate.getInstructorId());

        if(StringUtils.isNotBlank(drivingInstructorUpdate.getInstructorName())){
            wrapper.set(DrivingInstructor::getInstructorName,drivingInstructorUpdate.getInstructorName());
        }
        if(StringUtils.isNotBlank(drivingInstructorUpdate.getPhone())){
            wrapper.set(DrivingInstructor::getPhone,drivingInstructorUpdate.getPhone());
        }
        if (StringUtils.isNotBlank(drivingInstructorUpdate.getIdNumber())){
            wrapper.set(DrivingInstructor::getIdNumber, drivingInstructorUpdate.getIdNumber());
        }
        if(StringUtils.isNotBlank(drivingInstructorUpdate.getTeachingYears())){
            wrapper.set(DrivingInstructor::getTeachingYears,drivingInstructorUpdate.getTeachingYears());
        }
        if(StringUtils.isNotBlank(drivingInstructorUpdate.getGoodSubject())){
            wrapper.set(DrivingInstructor::getGoodSubject,drivingInstructorUpdate.getGoodSubject());
        }
        if(StringUtils.isNotBlank(drivingInstructorUpdate.getPhoto())){
            wrapper.set(DrivingInstructor::getPhoto, drivingInstructorUpdate.getPhoto());
        }
        int update = baseMapper.update(null, wrapper);
        if (update<=0){
            throw new RuntimeException("更新失败");
        }
        DrivingInstructor instructor = baseMapper.selectById(drivingInstructorUpdate.getInstructorId());
        if (instructor==null){
            throw new RuntimeException("该教练不存在");
        }
        return getDrivingInstructorVo(instructor);
    }

    private static DrivingInstructorListVo getDrivingInstructorVo(DrivingInstructor drivingInstructor) {
        DrivingInstructorListVo drivingInstructorListVo = new DrivingInstructorListVo();
        drivingInstructorListVo.setIdNumber(String.valueOf(drivingInstructor.getIdNumber()));
        drivingInstructorListVo.setInstructorName(drivingInstructor.getInstructorName());
        drivingInstructorListVo.setCertificate(drivingInstructor.getCertificate());
        drivingInstructorListVo.setGoodSubject(drivingInstructor.getGoodSubject());
        drivingInstructorListVo.setEntryDate(drivingInstructor.getEntryDate());
        drivingInstructorListVo.setTeachingYears(drivingInstructor.getTeachingYears());
        drivingInstructorListVo.setPhone(drivingInstructor.getPhone());
        drivingInstructorListVo.setPhoto(drivingInstructor.getPhoto());
        drivingInstructorListVo.setInstructorId(drivingInstructor.getInstructorId());
        return drivingInstructorListVo;
    }
}
