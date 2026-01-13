package com.mashang.mashangdriving.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mashang.mashangdriving.domain.entity.DrivingCourse;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingCourseCreate;
import com.mashang.mashangdriving.domain.param.student.create.AddRating;
import com.mashang.mashangdriving.domain.vo.student.ContactInstructorVo;
import com.mashang.mashangdriving.domain.vo.student.MyAppointmentDtlVo;
import com.mashang.mashangdriving.mapping.manager.DrivingCourseMapping;
import com.mashang.mashangdriving.service.manager.IAppointmentService;
import com.mashang.mashangdriving.service.manager.IDrivingLocationService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "学员端--我的预约")
@RestController
@RequestMapping("/appointment")
public class MyAppointmentController extends BaseController {

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private IDrivingLocationService locationService;

    @ApiOperation(value = "查询我所有预约",notes = "可以根据预约状态进行查询")
    @GetMapping("my/all")
    public R<List<MyAppointmentDtlVo>> getMyAllAppointment(String status){

        return R.ok(appointmentService.myAllAppointment(status));
    }

    @ApiOperation("联系教练")
    @GetMapping("/contact/instructor/{appointmentId}")
    @ApiImplicitParam(name = "appointmentId",value = "预约id")
    public R<ContactInstructorVo> getContactInstructor(@PathVariable Long appointmentId){

        return R.ok(appointmentService.getContactInstructor(appointmentId));
    }

    @ApiOperation("取消预约")
    @DeleteMapping("/{appointmentId}")
    @ApiImplicitParam(name = "appointmentId",value = "预约id")
    public R deleteAppointment(@PathVariable Long appointmentId) {

        return toR(appointmentService.deleteAppointment(appointmentId));
    }

    @ApiOperation("评价教练")
    @PostMapping("/create/rating")
    public R createRating(@RequestBody @Validated AddRating addRating) {

        return toR(appointmentService.createRating(addRating));
    }

    @ApiOperation(value = "Ai智能分析")
    @PostMapping("/smart/analysis")
    public R smartAnalysis() {

        return R.ok(appointmentService.smartAnalysis());
    }

}
