package com.mashang.mashangdriving.controller.student;

import com.mashang.mashangdriving.domain.vo.student.MyInstructorVo;
import com.mashang.mashangdriving.service.student.IMyInstructorService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myInstructor")
@Api(tags = "学员端--我的教练")
public class MyInstructorController extends BaseController {
    @Autowired
    private IMyInstructorService myInstructorService;

    @GetMapping
    @ApiOperation("查询我的教练")
    public R getMyInstructor(Long studentId) {
        MyInstructorVo myInstructorVo = myInstructorService.selectMyInstructorById(studentId);
        if (myInstructorVo == null) {
            return R.fail("该学员没有相对应的教练");
        }
        return R.ok(myInstructorVo);
    }
}
