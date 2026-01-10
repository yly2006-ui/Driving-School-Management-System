package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingInstructor;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingInstructorCreate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingInstructorUpdate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingInstructorListVo;
import com.mashang.mashangdriving.service.manager.IDrivingInstructorService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "管理端--教练管理")
@RequestMapping("/instructorManager")
public class DrivingInstructorController extends BaseController {

    @Autowired
    private IDrivingInstructorService drivingInstructorService;

    @GetMapping("/list")
    @ApiOperation("分页查询教练列表")
    public TableDataInfo<DrivingInstructorListVo> list(PageQuery pageQuery) {
        Page<DrivingInstructorListVo> drivingInstructorListVoPage = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        Page<DrivingInstructorListVo> list = drivingInstructorService.getList(drivingInstructorListVoPage);
        return getDataTable(list.getRecords(),list.getTotal());
    }

    @GetMapping("/selectOne")
    @ApiOperation("查询教练详细信息")
    public R selectOne(@RequestParam("instructorName") String instructorName) {
        DrivingInstructorListVo instructor = drivingInstructorService.getByName(instructorName);
        if (instructor == null)  {
            return R.fail("该用户不存在");
        }
        return R.ok(instructor);
    }

    @PostMapping("/createInstructor")
    @ApiOperation("新增教练")
    public R insert( DrivingInstructorCreate drivingInstructorCreate) {
        DrivingInstructorListVo insert = drivingInstructorService.insert(drivingInstructorCreate);
        if (insert == null) {
            return R.fail("新增失败");
        }
        return R.ok(insert);
    }
    @PutMapping("/updateInstructor")
    @ApiOperation("修改教练")
    public R update( DrivingInstructorUpdate drivingInstructorUpdate) {
        DrivingInstructorListVo update = drivingInstructorService.update(drivingInstructorUpdate);
        if (update == null) {
            return R.fail("修改失败");
        }
        return R.ok(update);
    }


}
