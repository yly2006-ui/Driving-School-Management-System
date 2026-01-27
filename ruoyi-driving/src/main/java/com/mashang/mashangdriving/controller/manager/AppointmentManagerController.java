package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.param.manager.query.ManagerAppointmentQuery;
import com.mashang.mashangdriving.domain.vo.manager.ManagerAppointmentListVo;
import com.mashang.mashangdriving.domain.vo.student.AllInstructorListVo;
import com.mashang.mashangdriving.domain.vo.student.AllSubjectVo;
import com.mashang.mashangdriving.service.impl.student.AppointmentPeakVO;
import com.mashang.mashangdriving.service.manager.IAppointmentService;
import com.mashang.mashangdriving.service.manager.IInstructorService;
import com.mashang.mashangdriving.service.student.ISubjectService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        int i = appointmentService.completedTodayApproval();
        System.out.println(i);
        return R.ok(appointmentService.cancelApproval());
    }

    @ApiOperation(value = "预约列表",notes = "可以根据预约状态、学员姓名、手机号进行那个模糊以及根据教练名称和科目名称进行查询")
    @GetMapping("all/approval")
    /**
     * TableDataInfo<List<ManagerAppointmentListVo>> 改为 TableDataInfo<ManagerAppointmentListVo>
     *     rows 本身就是 List<T>
     *     泛型 T 只作用在 rows 上
     */
    public TableDataInfo<ManagerAppointmentListVo> allApproval(@Validated PageQuery pageQuery, ManagerAppointmentQuery managerAppointmentQuery){

        Page<ManagerAppointmentListVo> page =
                appointmentService.page(pageQuery, managerAppointmentQuery);

        TableDataInfo<ManagerAppointmentListVo> rspData = new TableDataInfo<>();
        rspData.setRows(page.getRecords());
        rspData.setTotal(page.getTotal());
        rspData.setCode(200);
        return rspData;
    }

    @ApiOperation(value = "本周预约高峰统计结果",notes = "count ≥ 10  → 高峰" + "5 ≤ count < 10 → 中峰" + "count < 5 → 低峰")
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

    @ApiOperation("预约详情")
    @GetMapping("/appointment/dtl/{appointmentId}")
    @ApiImplicitParam(name = "appointmentId",value = "预约id")
    public R<ManagerAppointmentListVo> appointmentDtl(@PathVariable Long appointmentId){

        return R.ok(appointmentService.appointmentDtl(appointmentId));
    }

    @ApiOperation("通过预约")
    @PutMapping("/update/status/{appointmentId}")
    @ApiImplicitParam(name = "appointmentId",value = "预约id")
    public R updateStatus(@PathVariable Long appointmentId) {

        return toR(appointmentService.updateStatus(appointmentId));
    }

    @ApiOperation("拒绝预约")
    @DeleteMapping("delete/{appointmentId}")
    @ApiImplicitParam(name = "appointmentId",value = "预约id")
    public R deleteCourse(@PathVariable Long appointmentId) {

        return toR(appointmentService.managerDeleteAppointment(appointmentId));
    }

    @ApiOperation(value = "Ai下周预测",notes = "count ≥ 10  → 高峰" + "5 ≤ count < 10 → 中峰" + "count < 5 → 低峰")
    @PostMapping("/chat/appointment")
    public R chatAppointment() {

        return R.ok(appointmentService.getNextWeeklyAppointmentPeaks());
    }

    @ApiOperation(value = "Ai智能分析")
    @PostMapping("/smart/analysis")
    public R smartAnalysis() {

        return R.ok(appointmentService.smartAnalysis());
    }
}
