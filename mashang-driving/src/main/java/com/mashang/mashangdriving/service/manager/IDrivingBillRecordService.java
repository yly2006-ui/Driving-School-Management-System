package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillRecordListVo;
import org.apache.ibatis.annotations.Param;

public interface IDrivingBillRecordService extends IService<DrivingBillRecord> {
    // 分页查询所有财务信息
    Page<DrivingBillRecordListVo> queryBillRecord(@Param("page") Page<DrivingBillRecordListVo> page,
                                        @Param(Constants.WRAPPER) QueryWrapper<DrivingBillRecordListVo>
                                                wrapper);
}
