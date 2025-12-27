package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingBillRecordQuery;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillRecordListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillYearMessageVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface IDrivingBillRecordService extends IService<DrivingBillRecord> {
    // 分页查询所有财务信息
    Page<DrivingBillRecordListVo> queryBillRecord(@Param("page") Page<DrivingBillRecordListVo> page,
                                                  @Param("query") DrivingBillRecordQuery query);

    //查询年度总表头
    DrivingBillYearMessageVo queryAll(Integer year);

}
