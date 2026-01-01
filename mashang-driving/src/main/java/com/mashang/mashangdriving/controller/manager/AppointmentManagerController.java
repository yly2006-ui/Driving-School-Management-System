package com.mashang.mashangdriving.controller.manager;

import com.mashang.mashangdriving.domain.vo.manager.ManagerDataOverviewDtlVo;
import com.mashang.mashangdriving.domain.vo.student.AllInstructorListVo;
import com.mashang.mashangdriving.domain.vo.student.AllSubjectVo;
import com.mashang.mashangdriving.service.impl.student.AppointmentPeakVO;
import com.mashang.mashangdriving.service.manager.IAppointmentService;
import com.mashang.mashangdriving.service.manager.IInstructorService;
import com.mashang.mashangdriving.service.student.ISubjectService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "管理端--预约管理")
@RestController
@RequestMapping("/home")
public class AppointmentManagerController extends BaseController {

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private IInstructorService instructorService;

    @Autowired
    private ISubjectService subjectService;

    @ApiOperation("待审核预约条数")
    @GetMapping("/awaiting/approval")
    public R awaitingApproval(){

        return R.ok(appointmentService.awaitingApproval());
    }

    @ApiOperation("已确认预约条数")
    @GetMapping("/confirm/approval")
    public R confirmApproval(){
        return R.ok(appointmentService.confirmApproval());
    }

    @ApiOperation("今日完成预约条数")
    @GetMapping("/completed/approval")
    public R completedTodayApproval(){
        return R.ok(appointmentService.completedTodayApproval());
    }

    @ApiOperation("已取消预约条数")
    @GetMapping("/cancel/approval")
    public R cancelApproval(){
        return R.ok(appointmentService.completedTodayApproval());
    }

    @ApiOperation(value = "预约列表",notes = "可以根据预约状态、学员姓名、手机号进行那个模糊查看所有预约(查看已取消状态切勿用该接口)")
    @GetMapping("all/approval")
    public R allApproval(){
        return null;
    }

    @ApiOperation(value = "已取消预约列表",notes = "专门查看已取消状态的所有预约)")
    @GetMapping("all/cancel/approval")
    public R allCancelApproval(){
        return null;
    }

    @ApiOperation("本周预约高峰统计结果")
    @GetMapping("/weeklyPeak")
    public R weeklyPeak() {

        List<AppointmentPeakVO> peaks =
                appointmentService.getWeeklyAppointmentPeaks();

        return R.ok(peaks);
    }







    @ApiOperation("查询所有科目")
    @GetMapping("/subject")
    public R<List<AllSubjectVo>> allSubject(){

        return R.ok(subjectService.allSubject());
    }

    @ApiOperation("查询所有教练")
    @GetMapping("/instructor")
    public R allInstructor(){

        List<AllInstructorListVo> allInstructorListVos = instructorService.allnstructorList();

        return R.ok(allInstructorListVos);
    }
}
