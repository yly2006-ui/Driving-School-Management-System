package com.mashang.mashangdriving.controller.student;

import com.mashang.mashangdriving.domain.vo.student.ContactInstructorVo;
import com.mashang.mashangdriving.domain.vo.student.MyAppointmentDtlVo;
import com.mashang.mashangdriving.service.manager.IAppointmentService;
import com.mashang.mashangdriving.service.manager.IDrivingLocationService;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "学员端--我的预约")
@RestController
@RequestMapping("/appointment")
public class MyAppointmentController {

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private IDrivingLocationService locationService;

    @ApiOperation("查询我所有预约")
    @GetMapping("my/all")
    public R<List<MyAppointmentDtlVo>> getMyAllAppointment(){

        return R.ok(appointmentService.myAllAppointment());
    }

    @ApiOperation("联系教练")
    @GetMapping("/contact/instructor/{appointmentId}")
    @ApiImplicitParam(name = "appointmentId",value = "预约id")
    public R<ContactInstructorVo> getContactInstructor(@PathVariable Long appointmentId){

        return R.ok(appointmentService.getContactInstructor(appointmentId));
    }


}
