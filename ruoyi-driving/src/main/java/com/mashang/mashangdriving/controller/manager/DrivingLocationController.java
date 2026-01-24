package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.Area;
import com.mashang.mashangdriving.domain.entity.DrivingLocation;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingLocationCreate;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingLocationQuery;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingLocationUpdate;
import com.mashang.mashangdriving.domain.vo.manager.AreaListVO;
import com.mashang.mashangdriving.domain.vo.manager.DrivingLocationDtlVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingLocationListVo;
import com.mashang.mashangdriving.mapping.manager.AreaMapping;
import com.mashang.mashangdriving.mapping.manager.DrivingLocationMapping;
import com.mashang.mashangdriving.service.manager.IAreaService;
import com.mashang.mashangdriving.service.manager.IDrivingLocationService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "管理端--地点管理")
@RestController
@RequestMapping("/drivingLocation")
public class DrivingLocationController extends BaseController {

    @Autowired
    private IDrivingLocationService drivingLocationService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private IAreaService areaService;



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
                                                           DrivingLocationQuery drivingLocationQuery) {
        LambdaQueryWrapper<DrivingLocation> lqw = new LambdaQueryWrapper<>();

        // 核心修改：三合一模糊匹配（官方正确写法）
        String searchKey = drivingLocationQuery.getAddressOrMasterOrLocationName();
        if (StringUtils.isNotEmpty(searchKey)) {
            // 用官方推荐的嵌套方式加括号，避免and(true)/and(false)的错误用法
            lqw.and(wrapper -> wrapper
                    .like(DrivingLocation::getLocationName,searchKey )
                    .or()
                    .like(DrivingLocation::getAddress, searchKey)
                    .or()
                    .like(DrivingLocation::getMaster, searchKey)
            );
        }

        // 以下代码保持不变
        lqw.eq(StringUtils.isNotEmpty(drivingLocationQuery.getStatus()), DrivingLocation::getStatus,
                drivingLocationQuery.getStatus());
        lqw.eq(DrivingLocation::getDelFlag, 0);
        lqw.orderByDesc(DrivingLocation::getLocationId);

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
            String s = dictDataService.selectDictLabel("place_status", dtl.getStatus());
            dtl.setStatusName(s);
            String typeName = dictDataService.selectDictLabel("place_type", dtl.getLocationTypeId());
            dtl.setLocationTypeName(typeName);
            return R.ok(dtl);
        }return R.fail();
    }


    @ApiOperation("查询所有省")
    @GetMapping("area/select")
    public R<List<AreaListVO>>select(){
        LambdaQueryWrapper<Area>areaLambdaQueryWrapper=new LambdaQueryWrapper<>();
        areaLambdaQueryWrapper.eq(Area::getLevel,"1");
        List<Area> list = areaService.list(areaLambdaQueryWrapper);

        if (CollectionUtils.isEmpty(list)){
            return R.fail("未查询到省份");
        }
        return R.ok(AreaMapping.INSTANCE.tolist(list));

    }

    @ApiOperation("查询所有市")
    @GetMapping("city/select")
    public R<List<AreaListVO>>selectCity(@ApiParam("省id") Long parentId){

        LambdaQueryWrapper<Area>areaLambdaQueryWrapper=new LambdaQueryWrapper<>();
        areaLambdaQueryWrapper.eq(Area::getLevel,"2");
        areaLambdaQueryWrapper.eq(Area::getParentId,parentId);
        List<Area> list = areaService.list(areaLambdaQueryWrapper);

        if (CollectionUtils.isEmpty(list)){
            return R.fail("未查询到地级市");
        }
        return R.ok(AreaMapping.INSTANCE.tolist(list));

    }

    @ApiOperation("查询所有县")
    @GetMapping("county/select")
    public R<List<AreaListVO>>selectcounty(@ApiParam("市id") Long parentId){

        LambdaQueryWrapper<Area>areaLambdaQueryWrapper=new LambdaQueryWrapper<>();
        areaLambdaQueryWrapper.eq(Area::getLevel,"3");
        areaLambdaQueryWrapper.eq(Area::getParentId,parentId);
        List<Area> list = areaService.list(areaLambdaQueryWrapper);

        if (CollectionUtils.isEmpty(list)){
            return R.fail("未查询到区县");
        }
        return R.ok(AreaMapping.INSTANCE.tolist(list));

    }

}
