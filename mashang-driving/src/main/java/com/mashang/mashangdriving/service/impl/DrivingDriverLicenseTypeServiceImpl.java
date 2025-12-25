package com.mashang.mashangdriving.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingDriverLicenseType;
import com.mashang.mashangdriving.mapper.DrivingDriverLicenseTypeMapper;
import com.mashang.mashangdriving.service.IDrivingDriverLicenseTypeService;
import org.springframework.stereotype.Service;

@Service
public class DrivingDriverLicenseTypeServiceImpl extends ServiceImpl<DrivingDriverLicenseTypeMapper, DrivingDriverLicenseType> implements IDrivingDriverLicenseTypeService {
}
