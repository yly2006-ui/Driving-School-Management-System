package com.mashang.mashangdriving.controller.manager;

import com.mashang.mashangdriving.domain.vo.manager.AreaListVO;
import com.mashang.mashangdriving.service.manager.IAreaService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Api(tags = "管理端地点管理--地区管理")
@RestController
@RequestMapping("/area")
public class AreaController extends BaseController {

    @Autowired
    private IAreaService areaService;

    @ApiOperation("嵌套查询")
    @GetMapping("/select")
    public TableDataInfo<List<AreaListVO> >select(){
        List<AreaListVO> select = areaService.select();
        return getDataTable(select);
    }
}
