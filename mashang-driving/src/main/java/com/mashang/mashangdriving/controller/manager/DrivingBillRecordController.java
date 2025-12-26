package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingBillRecordQuery;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillRecordListVo;
import com.mashang.mashangdriving.service.manager.IDrivingBillRecordService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Api(tags = "管理端--财务管理")
@RestController
@RequestMapping("/drivingBillRecord")
public class DrivingBillRecordController extends BaseController {

    @Autowired
    private IDrivingBillRecordService drivingBillRecordService;

    @ApiOperation("分页查询财务信息")
    @GetMapping("/list")
    public TableDataInfo<List<DrivingBillRecordListVo>> select (@Validated PageQuery pageQuery,
                                                                DrivingBillRecordQuery drivingBillRecordQuery){
        Page<DrivingBillRecordListVo> page =new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        QueryWrapper<DrivingBillRecordListVo>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(drivingBillRecordQuery.getUserName()),
                "driving_student.student_name",drivingBillRecordQuery.getUserName());
        queryWrapper.eq(StringUtils.isNotEmpty(drivingBillRecordQuery.getRoleName()),
                "role.role_name",drivingBillRecordQuery.getRoleName());
        queryWrapper.eq(StringUtils.isNotEmpty(drivingBillRecordQuery.getPaymentMethod()),
                "pay.pay_type",drivingBillRecordQuery.getPaymentMethod());
        queryWrapper.eq(StringUtils.isNotEmpty((CharSequence) drivingBillRecordQuery.getPaymentTime()),
                "pay.create_time",drivingBillRecordQuery.getPaymentTime());

        Page<DrivingBillRecordListVo> query = drivingBillRecordService.queryBillRecord(page, queryWrapper);
        return getDataTable(query.getRecords(), query.getTotal());
    }


}
