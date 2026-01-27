package com.mashang.mashangdriving.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mashang.mashangdriving.domain.entity.DrivingInstructor;
import com.mashang.mashangdriving.domain.param.manager.create.CreateStudentAppointment;
import com.mashang.mashangdriving.domain.vo.manager.ReservationSlot;
import com.mashang.mashangdriving.domain.vo.student.AllInstructorListVo;
import com.mashang.mashangdriving.domain.vo.student.StudentAppointmentVo;
import com.mashang.mashangdriving.domain.vo.student.TimeSlotVO;
import com.mashang.mashangdriving.mapping.manager.InstructorMapping;
import com.mashang.mashangdriving.service.manager.IAppointmentService;
import com.mashang.mashangdriving.service.manager.IInstructorService;
import com.mashang.mashangdriving.service.student.IStudentService;
import com.ruoyi.common.constant.InstructorConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(tags = "学员端--预约")
@RestController
@RequestMapping("/student")
public class StudentAppointmentController extends BaseController {

    @Autowired
    private IInstructorService instructorService;

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private IStudentService studentService;

    @ApiOperation("添加预约")
    @PostMapping("/create/instructor")
    public R<StudentAppointmentVo> createStudentAppointment(@RequestBody @Validated CreateStudentAppointment createStudentAppointment){

        return R.ok(appointmentService.createAppointment(createStudentAppointment));
    }

    @ApiOperation("查询所有教练")
    @GetMapping("/all/instructor")
    public R allInstructor(){

        List<AllInstructorListVo> allInstructorListVos = instructorService.allnstructorList();

        return R.ok(allInstructorListVos);
    }

    @ApiOperation("我的教练---(科目二)")
    @GetMapping("/my/instructor/two")
    public R myInstructorTwo(){

        return R.ok(instructorService.myInstructor());
    }

    @ApiOperation("我的教练---(科目三)")
    @GetMapping("/my/instructor/three")
    public R myInstructorThree(){

        return R.ok(instructorService.myThreeInstructor());
    }

    @ApiOperation("在职教练预约时间安排")
    @GetMapping("/instructor/time/{instructorId}")
    @ApiImplicitParam(name = "instructorId",value = "教练id")
    public R<ReservationSlot> reservationSlot(@PathVariable Long instructorId){

        LambdaQueryWrapper<DrivingInstructor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DrivingInstructor::getStatus, InstructorConstants.ON_STATUS);
        wrapper.eq(DrivingInstructor::getInstructorId, instructorId);
        DrivingInstructor instructor = instructorService.getOne(wrapper);

        ReservationSlot slot = InstructorMapping.INSTANCE.toSlot(instructor);
        slot.setInstructorId(instructorId);

        return R.ok(slot);
    }

    /**
     * 前端传日期 Date
     *    ↓
     * 查询该日期的历史预约数据（startTime / endTime）
     *    ↓
     * 后端整理成 AI 可理解的 JSON
     *    ↓
     * AI 分析哪些时段预约少（低峰）
     *    ↓
     * AI 只返回低峰时间段 JSON
     *    ↓
     * 后端解析 → 返回给前端
     * @param date
     * @return
     */
    @ApiOperation("ai智能生成低峰时段")
    @GetMapping("/ai/low/peak/time")
    @ApiImplicitParam(name = "Date",value = "预约日期",example = "yyyy-MM-dd")
    public R lowPeakTimeSlots(
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date date
    ) {
        return R.ok(appointmentService.aiRecommendLowPeakTimeSlots(date));
    }

    /**
     * 根据选中的时间段推荐教练
     */
    @ApiOperation("ai教练推荐")
    @PostMapping("/ai/instructor")
    public R recommendInstructor(@RequestBody List<TimeSlotVO> timeSlot) {
        return R.ok(
                appointmentService.recommendInstructor(timeSlot)
        );
    }

 }
