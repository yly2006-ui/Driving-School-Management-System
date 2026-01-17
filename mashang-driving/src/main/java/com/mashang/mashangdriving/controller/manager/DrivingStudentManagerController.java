package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.Exception.BusinessException;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingStudentCreate;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingStudentQuery;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingStudentManagerUpdate;
import com.mashang.mashangdriving.domain.param.student.update.DrivingStudentUpdate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo;
import com.mashang.mashangdriving.mapping.manager.DrivingStudentMapping;
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
    public TableDataInfo<DrivingStudentListVo> getList(PageQuery pageQuery) {
        Page<DrivingStudentListVo> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        Page<DrivingStudentListVo> result = drivingStudentManagerService.getList( page);
        return getDataTable(result.getRecords(),result.getTotal());
    }

    @GetMapping("/selectOne")
    @ApiOperation("查询学员个体")
    public R selectOne(@RequestParam(value = "studentName", required = false) String studentName,
                       @RequestParam(value = "phone", required = false) String phone,
                       @RequestParam(value = "idNumber", required = false) String idNumber) throws BusinessException {

        DrivingStudentQuery query = new DrivingStudentQuery();
        query.setStudentName(studentName);
        query.setPhone(phone);
        query.setIdNumber(idNumber);

        DrivingStudentListVo one = drivingStudentManagerService.selectOne(query);

        if (one == null) {
            return R.fail("查询为空");
        }
        return R.ok(one,"查询成功");
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


