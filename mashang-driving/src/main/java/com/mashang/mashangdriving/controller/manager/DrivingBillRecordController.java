package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingBillRecordQuery;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillRecordListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillYearMessageVo;
import com.mashang.mashangdriving.service.manager.IDrivingBillRecordService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
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

        Page<DrivingBillRecordListVo> query = drivingBillRecordService.queryBillRecord(page, drivingBillRecordQuery);
        return getDataTable(query.getRecords(), query.getTotal());
    }

    @ApiOperation("查询财务年度信息")
    @GetMapping("/year/queryAll")
    public R queryAll(@ApiParam("查询年度财务的时间")@RequestParam String year){

        DrivingBillYearMessageVo drivingBillYearMessageVo = drivingBillRecordService.queryAll(year);
        if (drivingBillYearMessageVo!=null){
            return R.ok(drivingBillYearMessageVo);
        }else {
            return R.fail();
        }
    }

}
