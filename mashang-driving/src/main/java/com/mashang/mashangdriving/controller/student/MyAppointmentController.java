package com.mashang.mashangdriving.controller.student;

import com.mashang.mashangdriving.domain.vo.student.MyAppointmentDtlVo;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "学员端--我的预约")
@RestController
@RequestMapping("/appointment")
public class MyAppointmentController {

    @ApiOperation("查询所有教练")
    @GetMapping("my/all")
    public R<List<MyAppointmentDtlVo>> getMyAllAppointment(){

        return null;
    }

}
