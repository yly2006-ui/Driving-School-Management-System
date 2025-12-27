package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingBillRecordQuery;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillRecordListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillYearMessageVo;
import org.apache.ibatis.annotations.Param;


public interface DrivingBillRecordMapper extends BaseMapper<DrivingBillRecord> {
    // 分页查询所有财务信息
//    Page<DrivingBillRecordListVo> queryBillRecord(@Param("page") Page<DrivingBillRecordListVo> page,
//                                                  @Param("query") DrivingBillRecordQuery query);
    // 分页查询所有财务信息
    Page<DrivingBillRecordListVo> queryBillRecord(@Param("page") Page<DrivingBillRecordListVo> page,
                                                  @Param(Constants.WRAPPER) Wrapper<DrivingBillRecord> wrapper);

    //查询年度总收入
    DrivingBillYearMessageVo queryAnnualTotalIncome(@Param(Constants.WRAPPER) Wrapper<DrivingBillRecord> wrapper);

    //查询年度总支出
    DrivingBillYearMessageVo queryAnnualTotalExpenditure(@Param(Constants.WRAPPER) Wrapper<DrivingBillRecord> wrapper);

    //查询学员总数
    DrivingBillYearMessageVo queryAllStudentCount(@Param(Constants.WRAPPER) Wrapper<DrivingBillRecord> wrapper);


}
