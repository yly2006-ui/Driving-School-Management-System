package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillRecordListVo;
import com.mashang.mashangdriving.mapper.manager.DrivingBillRecordMapper;
import com.mashang.mashangdriving.service.manager.IDrivingBillRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrivingBillRecordServiceImpl extends ServiceImpl<DrivingBillRecordMapper, DrivingBillRecord> implements IDrivingBillRecordService {
    @Autowired
    private DrivingBillRecordMapper drivingBillRecordMapper;

    @Override
    public Page<DrivingBillRecordListVo> queryBillRecord(Page<DrivingBillRecordListVo> page, QueryWrapper<DrivingBillRecordListVo> wrapper) {
        return drivingBillRecordMapper.queryBillRecord(page,wrapper);
    }
}
