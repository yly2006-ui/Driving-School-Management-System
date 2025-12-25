package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingDriverLicenseType;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingDriverLicenseTypeQuery;
import com.mashang.mashangdriving.domain.vo.manager.DrivingDriverLicenseTypeListVo;
import com.mashang.mashangdriving.mapping.manager.DrivingDirverLicenseTypeMapping;
import com.mashang.mashangdriving.service.manager.IDrivingDriverLicenseTypeService;
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
    public TableDataInfo<List<DrivingDriverLicenseTypeListVo>> list(@Validated PageQuery pageQuery,
                                                                    DrivingDriverLicenseTypeQuery driverLicenseTypeQuery){
        LambdaQueryWrapper<DrivingDriverLicenseType> lqw =new LambdaQueryWrapper<>();
        //过滤驾照类型代码
        lqw.eq(StringUtils.isNotEmpty(driverLicenseTypeQuery.getDriverLicenseCode()),
                DrivingDriverLicenseType::getDriverLicenseCode,driverLicenseTypeQuery.getDriverLicenseCode());
        //过滤驾照类型名称
        lqw.like(StringUtils.isNotEmpty(driverLicenseTypeQuery.getDriverLicenseName()),
                DrivingDriverLicenseType::getDriverLicenseName,driverLicenseTypeQuery.getDriverLicenseName());
        //过滤驾照难度状态
        lqw.eq(StringUtils.isNotEmpty(driverLicenseTypeQuery.getLearningDifficulty()),
                DrivingDriverLicenseType::getLearningDifficulty,driverLicenseTypeQuery.getLearningDifficulty());
        //过滤驾照状态
        lqw.eq(StringUtils.isNotEmpty(driverLicenseTypeQuery.getStatus()),
                DrivingDriverLicenseType::getStatus,driverLicenseTypeQuery.getStatus());
        //过滤是否逻辑删除
        lqw.eq(DrivingDriverLicenseType::getDelFlag, 0);

        Page<DrivingDriverLicenseType>page =new Page<>(pageQuery.getPageNum(),pageQuery.getPageSize());
        Page<DrivingDriverLicenseType> result = drivingDriverLicenseTypeService.page(page, lqw);

        List<DrivingDriverLicenseTypeListVo> listVo = DrivingDirverLicenseTypeMapping.INSTANCE.toListVo(result.getRecords());
        return getDataTable(listVo, result.getTotal());
    }





}
