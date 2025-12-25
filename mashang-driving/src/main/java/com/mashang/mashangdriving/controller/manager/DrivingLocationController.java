package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingLocation;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingLocationCreate;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingLocationQuery;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingLocationUpdate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingLocationDtlVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingLocationListVo;
import com.mashang.mashangdriving.mapping.manager.DrivingLocationMapping;
import com.mashang.mashangdriving.service.manager.IDrivingLocationService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Api(tags = "管理端--地点管理")
@RestController
@RequestMapping("/drivingLocation")
public class DrivingLocationController extends BaseController {

    @Autowired
    private IDrivingLocationService drivingLocationService;



    @ApiOperation("新增地点")
    @PostMapping("/create")
    public R create(@RequestBody DrivingLocationCreate drivingLocationCreate){

        DrivingLocation create = DrivingLocationMapping.INSTANCE.toCreate(drivingLocationCreate);

        boolean save = drivingLocationService.save(create);

        return toR(save);

    }

    @ApiOperation("修改地点")
    @PostMapping("/update")
    public R update(@RequestBody DrivingLocationUpdate drivingLocationUpdate){

        DrivingLocation create = DrivingLocationMapping.INSTANCE.toUpdate(drivingLocationUpdate);

        LambdaQueryWrapper<DrivingLocation>lqw=new LambdaQueryWrapper<>();
        lqw.eq(DrivingLocation::getLocationName,drivingLocationUpdate.getLocationName());
        lqw.ne(DrivingLocation::getLocationId,drivingLocationUpdate.getLocationId());
        long count = drivingLocationService.count(lqw);
        if (count>0){
            throw new RuntimeException("地点名称已存在");
        }
        boolean save = drivingLocationService.updateById(create);

        return toR(save);

    }
    @ApiOperation("分页查询地点")
    @GetMapping("/list")
    public TableDataInfo<List<DrivingLocationListVo>> list(@Validated PageQuery pageQuery,
                                                           DrivingLocationQuery drivingLocationQuery){
        LambdaQueryWrapper<DrivingLocation>lqw=new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotEmpty(drivingLocationQuery.getLocationName()),
                DrivingLocation::getLocationName,drivingLocationQuery.getLocationName());
        lqw.eq(StringUtils.isNotEmpty(drivingLocationQuery.getAddress()),DrivingLocation::getAddress,
                drivingLocationQuery.getAddress());
        lqw.eq(StringUtils.isNotEmpty(drivingLocationQuery.getMaster()),DrivingLocation::getMaster,
                drivingLocationQuery.getMaster());
        lqw.eq(StringUtils.isNotEmpty(drivingLocationQuery.getStatus()),DrivingLocation::getStatus,
                drivingLocationQuery.getStatus());
        Page<DrivingLocationListVo> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        Page<DrivingLocationListVo> query = drivingLocationService.query(page, lqw);
        return getDataTable(query.getRecords(), query.getTotal());
    }

    @ApiOperation("删除地点")
    @DeleteMapping("/delete/{locationId}")
    public R delete(@PathVariable Long locationId){
        boolean b = drivingLocationService.removeById(locationId);
        return toR(b);
    }

    @ApiOperation("查询地点详情")
    @GetMapping("dtl/{locationId}")
    public  R dtl(@PathVariable Long locationId){
        DrivingLocation byId = drivingLocationService.getById(locationId);
        DrivingLocationDtlVo dtl = DrivingLocationMapping.INSTANCE.toDtl(byId);
        if (dtl!=null){
            return R.ok(dtl);
        }return R.fail();
    }

}
