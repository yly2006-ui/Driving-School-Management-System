package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillRecordListVo;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;


public interface DrivingBillRecordMapper extends BaseMapper<DrivingBillRecord> {
    // 分页查询所有财务信息
    Page<DrivingBillRecordListVo> queryBillRecord(@Param("page") Page<DrivingBillRecordListVo> page,
                                        @Param(Constants.WRAPPER) QueryWrapper<DrivingBillRecordListVo>
                                                Wrapper);
}
