package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingCar;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingCarCreate;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingCarQuery;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingCarUpdate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCarListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCarListVo1;
import com.mashang.mashangdriving.domain.vo.manager.DrivingPayRecordVo;
import com.mashang.mashangdriving.service.manager.IDrivingCarService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(tags = "管理端--车辆管理")
@RequestMapping("/drivingCarManager")
public class DrivingCarController extends BaseController {
    @Autowired
    private IDrivingCarService drivingCarService;

    @GetMapping("/drivingCarList")
    @ApiOperation("查询车辆列表")
    public TableDataInfo<DrivingCarListVo> getDrivingCarList(PageQuery pageQuery) {
        Page<DrivingCarListVo> objectPage = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        Page<DrivingCarListVo> drivingCarByPage = drivingCarService.getDrivingCarByPage(objectPage);
        return getDataTable(drivingCarByPage.getRecords(), drivingCarByPage.getTotal());
    }

    @GetMapping("/getStats")
    @ApiOperation("查询所有车辆状态")
    public Map<String,Object> getCarStats(){
        return drivingCarService.getCarStats();
    }

    @GetMapping("/getCarList")
    @ApiOperation("查询车辆")
    public R getCarList(DrivingCarQuery  drivingCarQuery) {
        List<DrivingCar> list = drivingCarService.selectList(drivingCarQuery);
        if (list != null && list.size() > 0) {
            return R.ok(list);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/getCarOne")
    @ApiOperation("查询车辆详情")
    public R getOneCar(Long carId) {
        if (Objects.isNull(carId)) {
            return R.fail("carID不能为空");
        }
        return R.ok(drivingCarService.getOneCar(carId));
    }

    @PostMapping("/createCar")
    @ApiOperation("新增车辆")
    public R createCar(DrivingCarCreate create) {
        DrivingCarListVo vo = drivingCarService.insertCar(create);
        if (Objects.isNull(vo)) {
            return R.fail("新增失败");
        }
        return R.ok(vo);
    }

    @PutMapping
    @ApiOperation("修改车辆")
    public R updateCar(DrivingCarUpdate update) {
        DrivingCarListVo drivingCarListVo = drivingCarService.updateCar(update);
        if (Objects.isNull(drivingCarListVo)) {
            return R.fail("修改失败");
        }
        return R.ok(drivingCarListVo);
    }

    @GetMapping
    @ApiOperation("查询车辆维修/保养记录")
    public TableDataInfo<DrivingPayRecordVo> getCar(Long carId,PageQuery pageQuery) {
        if (Objects.isNull(carId)) {
            throw new RuntimeException("ID不存在");
        }
        Page<DrivingPayRecordVo> objectPage = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        Page<DrivingPayRecordVo> carPay = drivingCarService.getCarPay(carId,objectPage);

        TableDataInfo<DrivingPayRecordVo> dataInfo = new TableDataInfo<>();
        dataInfo.setRows(carPay.getRecords());
        dataInfo.setTotal(carPay.getTotal());
        return dataInfo;

    }

}
