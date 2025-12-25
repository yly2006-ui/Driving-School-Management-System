package com.mashang.mashangdriving.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingDriverLicenseType;
import com.mashang.mashangdriving.domain.vo.DrivingDriverLicenseTypeListVo;
import com.mashang.mashangdriving.mapping.DrivingDirverLicenseTypeMapping;
import com.mashang.mashangdriving.service.IDrivingDriverLicenseTypeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Api(tags = "管理端--驾照类型管理")
@RestController
@RequestMapping("/drivingDriverLicenseType")
public class DrivingDriverLicenseTypeController extends BaseController {

    @Autowired
    private IDrivingDriverLicenseTypeService drivingDriverLicenseTypeService;

    @ApiOperation("分页查询驾照类型")
    @GetMapping("/list")
    public TableDataInfo<List<DrivingDriverLicenseTypeListVo>> list(@Validated PageQuery pageQuery){
        LambdaQueryWrapper<DrivingDriverLicenseType> lambdaQueryWrapper
                =new LambdaQueryWrapper<>();

        Page<DrivingDriverLicenseType>page =new Page<>(pageQuery.getPageNum(),pageQuery.getPageSize());
        Page<DrivingDriverLicenseType> result = drivingDriverLicenseTypeService.page(page, lambdaQueryWrapper);

        List<DrivingDriverLicenseTypeListVo> listVo = DrivingDirverLicenseTypeMapping.INSTANCE.toListVo(result.getRecords());
        return getDataTable(listVo, result.getTotal());
    }





}
