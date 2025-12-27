//package com.mashang.mashangdriving.controller.student;
//
//import com.mashang.mashangdriving.service.manager.IInstructorService;
//import com.ruoyi.common.core.controller.BaseController;
//import com.ruoyi.common.core.domain.R;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@Api(tags = "学员端--预约")
//@RestController
//@RequestMapping("/student")
//public class StudentAppointmentController extends BaseController {
//
//    @Autowired
//    private IInstructorService instructorService;
//
//    @ApiOperation("添加预约")
//    @PostMapping("/create/instructor")
//    public R createStudentAppointment(){
//
//        return null;
//    }
//
//    @ApiOperation("查询所有教练")
//    @GetMapping("/all/instructor")
//    public R allInstructor(){
//
//        return null;
//    }
//
//    @ApiOperation("我的教练---(科目二)随机默认一个")
//    @GetMapping("/my/instructor/{subjectId}")
//    @ApiImplicitParam(name = "subjectId",value = "科目id")
//    //“我的教练” ≠ 已经绑定的唯一教练
//    //
//    //在【预约阶段】，“我的教练”指的是：
//    //当前学员「历史上服务过 / 系统优先推荐」的教练集合中的首选教练
//    public R myInstructortwo(@PathVariable Long subjectId){
//
//        return null;
//    }
//    @ApiOperation("我的教练---(科目三)根据科目二教练默认")
//    @GetMapping("/my/instructor/{subjectId}")
//    @ApiImplicitParam(name = "subjectId",value = "科目id")
//    //“我的教练” ≠ 已经绑定的唯一教练
//    //
//    //在【预约阶段】，“我的教练”指的是：
//    //当前学员「历史上服务过 / 系统优先推荐」的教练集合中的首选教练
//    public R myInstructor(@PathVariable Long subjectId){
//
//        return null;
//    }
// }
