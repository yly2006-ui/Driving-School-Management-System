package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;
import com.mashang.mashangdriving.domain.entity.DrivingPay;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingBillRecordQuery;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillMonthMessageVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillRecordListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingBillYearMessageVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingGroupMonthVo;
import com.mashang.mashangdriving.service.manager.IDrivingBillRecordService;
import com.mashang.mashangdriving.service.student.IPayService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Validated
@Slf4j
@Api(tags = "管理端--财务管理")
@RestController
@RequestMapping("/drivingBillRecord")
public class DrivingBillRecordController extends BaseController {

    @Autowired
    private IDrivingBillRecordService drivingBillRecordService;

    @Autowired
    private IPayService payService;


    @ApiOperation("分页查询财务信息")
    @GetMapping("/list")
    public TableDataInfo<List<DrivingBillRecordListVo>> select(@Validated PageQuery pageQuery,
                                                               @Validated DrivingBillRecordQuery drivingBillRecordQuery) {

        Page<DrivingBillRecordListVo> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        Page<DrivingBillRecordListVo> query = drivingBillRecordService.queryBillRecord(page, drivingBillRecordQuery);
        return getDataTable(query.getRecords(), query.getTotal());
    }

    @ApiOperation("年度财务汇总")
    @GetMapping("/year/queryAll")
    public R<DrivingBillYearMessageVo> queryAll(@ApiParam(value = "查询年度财务时间")@RequestParam
                                                    @NotBlank(message = "时间不可为空")
                                                    String year) {

        DrivingBillYearMessageVo drivingBillYearMessageVo = drivingBillRecordService.queryAll(year);
        if (drivingBillYearMessageVo != null) {
            return R.ok(drivingBillYearMessageVo);
        } else {
            return R.fail();
        }
    }

    @ApiOperation("年度财务汇总Excel")
    @Log(title = "年度财务汇总", businessType = BusinessType.EXPORT)
    @GetMapping("/exportYear")
    public void exportYear(HttpServletResponse response, @RequestParam String year) throws IOException {
        DrivingBillYearMessageVo data = drivingBillRecordService.queryAll(year);

        if (data != null) {
            new ExcelUtil<>(DrivingBillYearMessageVo.class)
                    .exportExcel(response, Collections.singletonList(data), year + "年度财务汇总");
        }
    }

    @ApiOperation("月度财务报表")
    @GetMapping("/month/query")
    public R<DrivingBillMonthMessageVo> query(@ApiParam(value = "查询月度财务的时间")
                                                  @RequestParam @NotBlank String yearAndMonth) {

        DrivingBillMonthMessageVo drivingBillMonthMessageVo = drivingBillRecordService.queryMonthAll(yearAndMonth);
        if (drivingBillMonthMessageVo != null) {
            return R.ok(drivingBillMonthMessageVo);
        } else {
            return R.fail();
        }
    }

    @ApiOperation("月度财务报表excel")
    @Log(title = "月度财务报表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportMonth")
    public void exportMonth(HttpServletResponse response, @RequestParam String yearAndMonth) throws IOException {
        DrivingBillMonthMessageVo data = drivingBillRecordService.queryMonthAll(yearAndMonth);

        if (data != null) {
            new ExcelUtil<>(DrivingBillMonthMessageVo.class)
                    .exportExcel(response, Collections.singletonList(data), yearAndMonth + "月度财务报表");
        }
    }

    @ApiOperation("财务报表excel")
    @Log(title = "财务报表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<DrivingBillRecord> list = drivingBillRecordService.list();


        if (list != null) {
            new ExcelUtil<>(DrivingBillRecord.class)
                    .exportExcel(response, list, LocalDateTime.now().
                            format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "财务报表");
        }
    }

    @ApiOperation("每月的收入查询")
    @GetMapping("/income/trend")
    public TableDataInfo<List<DrivingGroupMonthVo>> queryIncomeTrend(@RequestParam @NotBlank(message = "时间不可为空") String year) {
        List<DrivingGroupMonthVo> trendList = drivingBillRecordService.queryIncomeTrendByYear(year);
        return getDataTable(trendList);
    }


    @ApiOperation("查询支付记录")
    @GetMapping("select/pay")
    public TableDataInfo<DrivingPay> select(@Validated PageQuery pageQuery) {
        Page<DrivingPay> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        LambdaQueryWrapper<DrivingPay> drivingPayLambdaQueryWrapper = new LambdaQueryWrapper<>();
        drivingPayLambdaQueryWrapper.eq(DrivingPay::getBillStatus, "0");
        drivingPayLambdaQueryWrapper.orderByDesc(DrivingPay::getCreateTime);
        Page<DrivingPay> payPage = payService.page(page, drivingPayLambdaQueryWrapper);
        return getDataTable(payPage.getRecords(), payPage.getTotal());
    }


    @PostMapping("/save/{payId}")
    @ApiOperation("添加账单记录")
    public R save(@PathVariable Long payId) {

        int i = drivingBillRecordService.saveDrivingBillRecord(payId);

        return toR(i);

    }

}
