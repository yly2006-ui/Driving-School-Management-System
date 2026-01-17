package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingDriverLicenseType;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingDriverLicenseTypeCreate;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingDriverLicenseTypeQuery;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingDriverLicenseTypeUpdate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingDriverLicenseTypeDtlVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingDriverLicenseTypeListVo;
import com.mashang.mashangdriving.mapping.manager.DrivingDirverLicenseTypeMapping;
import com.mashang.mashangdriving.service.manager.IDrivingDriverLicenseTypeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.service.ISysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "管理端--驾照类型管理")
@RestController
@RequestMapping("/drivingDriverLicenseType")
public class DrivingDriverLicenseTypeController extends BaseController {

    @Autowired
    private IDrivingDriverLicenseTypeService drivingDriverLicenseTypeService;

    @Autowired
    private ISysDictDataService dictDataService;

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
        lqw.orderByDesc(DrivingDriverLicenseType::getDriverLicenseId);

        Page<DrivingDriverLicenseType>page =new Page<>(pageQuery.getPageNum(),pageQuery.getPageSize());
        Page<DrivingDriverLicenseType> result = drivingDriverLicenseTypeService.page(page, lqw);

        List<DrivingDriverLicenseTypeListVo> listVo = DrivingDirverLicenseTypeMapping.INSTANCE.toListVo
                (result.getRecords());

        for (DrivingDriverLicenseTypeListVo vo : listVo) {
            if (vo.getStatus()!=null){
                String s = dictDataService.selectDictLabel("user_status", vo.getStatus());
                vo.setStatusName(s);
            }
        }
        return getDataTable(listVo, result.getTotal());
    }

    @ApiOperation("新增驾照类型")
    @PostMapping("/create")
    public R createType(@RequestBody DrivingDriverLicenseTypeCreate drivingDriverLicenseTypeCreate){

        DrivingDriverLicenseType create = DrivingDirverLicenseTypeMapping.INSTANCE.toCreate(drivingDriverLicenseTypeCreate);

        LambdaQueryWrapper<DrivingDriverLicenseType>lqw=new LambdaQueryWrapper<>();

        lqw.like(StringUtils.isNotEmpty(drivingDriverLicenseTypeCreate.getDriverLicenseName()),

                DrivingDriverLicenseType::getDriverLicenseName,drivingDriverLicenseTypeCreate.getDriverLicenseName());

        long count = drivingDriverLicenseTypeService.count(lqw);

        if (count>0){
            throw new RuntimeException("已存在此驾照类型，请勿重复添加");
        }

        boolean save = drivingDriverLicenseTypeService.save(create);

        return toR(save);
    }

    @ApiOperation("删除驾照类型")
    @DeleteMapping("/delete/{driverLicenseId}")
    public R deleteType(@PathVariable Long driverLicenseId){

        boolean b = drivingDriverLicenseTypeService.removeById(driverLicenseId);

        return toR(b);
    }

    @ApiOperation("修改驾照类型")
    @PutMapping("/update")
    public R updateType(@RequestBody DrivingDriverLicenseTypeUpdate drivingDriverLicenseTypeUpdate){
        DrivingDriverLicenseType updateType =
                DrivingDirverLicenseTypeMapping.INSTANCE.toupdate(drivingDriverLicenseTypeUpdate);

        LambdaQueryWrapper<DrivingDriverLicenseType>lqw=new LambdaQueryWrapper<>();

        lqw.eq(DrivingDriverLicenseType::getDriverLicenseName,drivingDriverLicenseTypeUpdate.getDriverLicenseName());
        lqw.ne(DrivingDriverLicenseType::getDriverLicenseId,drivingDriverLicenseTypeUpdate.getDriverLicenseId());

        long count = drivingDriverLicenseTypeService.count(lqw);

        if (count>0){
            throw new RuntimeException("已存在此驾照类型，无法修改");
        }
        boolean update = drivingDriverLicenseTypeService.updateById(updateType);

        return toR(update);
    }

    @ApiOperation("查询驾照类型详情")
    @GetMapping("/dtl/{driverLicenseId}")
    public R selectDtl(@PathVariable Long driverLicenseId){
        DrivingDriverLicenseType byId = drivingDriverLicenseTypeService.getById(driverLicenseId);
        DrivingDriverLicenseTypeDtlVo dtl = DrivingDirverLicenseTypeMapping.INSTANCE.toDtl(byId);
        if (dtl!=null){
           return R.ok(dtl);
        }
        return R.fail();
    }





}
