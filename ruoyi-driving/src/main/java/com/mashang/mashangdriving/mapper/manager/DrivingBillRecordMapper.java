package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillMonthMessageVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillRecordListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillYearMessageVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingGroupMonthVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DrivingBillRecordMapper extends BaseMapper<DrivingBillRecord> {

    // 分页查询所有财务信息
    Page<DrivingBillRecordListVo> queryBillRecord(@Param("page") Page<DrivingBillRecordListVo> page,
                                                  @Param(Constants.WRAPPER) Wrapper<DrivingBillRecord> wrapper);

    //查询年度总收入
    DrivingBillYearMessageVo queryAnnualTotalIncome(@Param(Constants.WRAPPER) Wrapper<DrivingBillRecord> wrapper);

    //查询年度总支出
    DrivingBillYearMessageVo queryAnnualTotalExpenditure(@Param(Constants.WRAPPER) Wrapper<DrivingBillRecord> wrapper);

    //查询学员总数
    DrivingBillYearMessageVo queryAllStudentCount(@Param(Constants.WRAPPER) Wrapper<DrivingBillRecord> wrapper);

    //查询月度收入
    DrivingBillMonthMessageVo queryMonthTotalIncome(@Param(Constants.WRAPPER) Wrapper<DrivingBillRecord> wrapper);

    //查询月支出
    DrivingBillMonthMessageVo queryMonthTotalExpenditure(@Param(Constants.WRAPPER) Wrapper<DrivingBillRecord> wrapper);

    //学员缴费
    DrivingBillMonthMessageVo queryStudentTotalIncome(@Param(Constants.WRAPPER) Wrapper<DrivingBillRecord> wrapper);


    //其他收入
    DrivingBillMonthMessageVo queryNotStudentTotalIncome(@Param(Constants.WRAPPER) Wrapper<DrivingBillRecord> wrapper);

    //每个月
    List<DrivingGroupMonthVo> queryIncomeByYearGroupByMonth(@Param("ew") QueryWrapper<DrivingBillRecord> queryWrapper);


    //完成培训学生
    Long finishedStudent(@Param(Constants.WRAPPER) Wrapper<DrivingBillRecord> wrapper);

    //查询roleid
    Long selectRoleId(Long userId);
}
