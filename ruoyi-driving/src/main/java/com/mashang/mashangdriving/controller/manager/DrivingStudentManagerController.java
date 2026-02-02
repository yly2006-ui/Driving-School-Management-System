package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.Exception.BusinessException;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingStudentCreate;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingStudentQuery;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingStudentManagerUpdate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo1;
import com.mashang.mashangdriving.service.manager.IDrivingStudentManagerService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "管理端--学员管理")
@RequestMapping("/studentManager")
public class DrivingStudentManagerController extends BaseController {

    @Autowired
    private IDrivingStudentManagerService drivingStudentManagerService;


    @GetMapping("/list")
    @ApiOperation("分页查询学员列表")
    public TableDataInfo<DrivingStudentListVo1> getList(PageQuery pageQuery) {
        Page<DrivingStudentListVo1> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        Page<DrivingStudentListVo1> result = drivingStudentManagerService.getList( page);
        return getDataTable(result.getRecords(),result.getTotal());
    }

    @GetMapping("/selectOne")
    @ApiOperation("查询学员个体")
    public TableDataInfo<DrivingStudentListVo1> selectOne(DrivingStudentQuery query,PageQuery pageQuery )  {
        Page<DrivingStudentListVo1> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        Page<DrivingStudentListVo1> page1 = drivingStudentManagerService.selectOne(query, page);
        if (page1.getTotal() <= 0) {
            return new TableDataInfo<>();
        }
        return getDataTable(page1.getRecords(),page1.getTotal());

    }

    @PostMapping("/createStudent")
    @ApiOperation("新增学员")
    public R createStudent( DrivingStudentCreate dto)  {
        DrivingStudentListVo drivingStudentListVo = drivingStudentManagerService.insertStudent(dto);
        if (drivingStudentListVo == null) {
            return R.fail("新增失败");
        }
        return R.ok(drivingStudentListVo);
    }

    @PutMapping("/updateStudent")
    @ApiOperation("修改学员")
    public R updateStudent(DrivingStudentManagerUpdate dto) {
        DrivingStudentListVo drivingStudentListVo = drivingStudentManagerService.updateStudent(dto);
        if (drivingStudentListVo == null) {
            return R.fail("修改失败");
        }
        return R.ok(drivingStudentListVo);
    }

    @DeleteMapping("/studentId")
    @ApiOperation("删除学员信息")
    public R studentId(@RequestParam(value = "studentId", required = true) Long studentId) {
        int i = drivingStudentManagerService.deleteById(studentId);
        if (i>0){
            return R.ok("删除成功");
        }
        return R.fail("删除失败");
    }

}


