package com.mashang.mashangdriving.controller.student;

import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.param.student.update.DrivingStudentUpdate;
import com.mashang.mashangdriving.domain.vo.student.DrivingStudentDtlVo;
import com.mashang.mashangdriving.mapping.student.DrivingStudentMapping;
import com.mashang.mashangdriving.service.student.IDrivingStudentService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
@Api(tags = "学生端--个人信息")
@RestController
@RequestMapping("/drivingStudent")
public class DrivingStudentController extends BaseController {

    @Autowired
    private IDrivingStudentService drivingStudentService;

    @ApiOperation("查询个人信息详情")
    @GetMapping("/list/{studentId}")
    public R select(@PathVariable Long studentId){
        DrivingStudentDtlVo drivingStudentDtlVo = drivingStudentService.selectById(studentId);
        if (drivingStudentDtlVo!=null){
            return R.ok(drivingStudentDtlVo);
        }else {
            return R.fail();
        }
    }

    @ApiOperation("修改个人信息")
    @PutMapping("/update")
    public  R update(@RequestBody DrivingStudentUpdate drivingStudentUpdate){
        DrivingStudent update = DrivingStudentMapping.INSTANCE.toUpdtae(drivingStudentUpdate);
        boolean b = drivingStudentService.updateById(update);
        return toR(b);
    }

}
