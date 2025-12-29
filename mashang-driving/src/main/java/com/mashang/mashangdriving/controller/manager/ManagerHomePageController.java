package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mashang.mashangdriving.domain.entity.DrivingNotice;
import com.mashang.mashangdriving.domain.vo.manager.ManagerDataOverviewDtlVo;
import com.mashang.mashangdriving.domain.vo.manager.DataOverviewNoticeDtlVo;
import com.mashang.mashangdriving.domain.vo.student.StudentDataOverviewDtlVo;
import com.mashang.mashangdriving.domain.vo.student.StudentDataOverviewNoticeDtlVo;
import com.mashang.mashangdriving.mapping.manager.NoticeMapping;
import com.mashang.mashangdriving.service.manager.IAppointmentService;
import com.mashang.mashangdriving.service.manager.IBillRecordService;
import com.mashang.mashangdriving.service.manager.IInstructorService;
import com.mashang.mashangdriving.service.manager.INoticeService;
import com.mashang.mashangdriving.service.student.IStudentService;
import com.ruoyi.common.constant.NoticeConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "首页")
@RestController
@RequestMapping("/home")
public class ManagerHomePageController extends BaseController {

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private IBillRecordService billRecordService;

    @Autowired
    private IAppointmentService appointmentService;

    @ApiOperation("管理端----数据概览")
    @GetMapping("/manager/overview")
    public R<ManagerDataOverviewDtlVo> managerDataOverview(){

        //当下的所有人数
        int allCount = (int) studentService.count();
        int lastMonthStudentMount = studentService.lastMonthStudentMount();
        int countLastMonthActiveStudent = studentService.countLastMonthActiveStudent();
        int countOnMonthActiveStudent = studentService.countOnMonthActiveStudent();
        double lastMonthTotalIncome = billRecordService.getLastMonthTotalIncome();
        double onMonthTotalIncome = billRecordService.selectOnMonthTotalIncome();
        int countYesterdayStatusOne = appointmentService.countYesterdayStatusOne();
        int countOnDayStatusOne = appointmentService.countOnDayStatusOne();

        LambdaQueryWrapper<DrivingNotice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DrivingNotice::getStatus, NoticeConstants.NORMAL_STATUS);
        List<DrivingNotice> noticeList = noticeService.list(wrapper);
        List<DataOverviewNoticeDtlVo> dataOverviewNoticeDtlVo =
                NoticeMapping.INSTANCE.toDataOverviewNoticeDtlVo(noticeList);

        ManagerDataOverviewDtlVo managerDataOverviewDtlVo = new ManagerDataOverviewDtlVo();

        managerDataOverviewDtlVo.setStudentNumber(allCount);
        managerDataOverviewDtlVo.setLearnStudent(countOnMonthActiveStudent);
        managerDataOverviewDtlVo.setTotalRevenue(onMonthTotalIncome);
        managerDataOverviewDtlVo.setPendingAppointments(countOnDayStatusOne);
        managerDataOverviewDtlVo.setLastMonthStudentNumber(lastMonthStudentMount);
        managerDataOverviewDtlVo.setLastMonthLearnStudent(countLastMonthActiveStudent);
        managerDataOverviewDtlVo.setLastMonthTotalRevenue(lastMonthTotalIncome);
        managerDataOverviewDtlVo.setLastDayPendingAppointments(countYesterdayStatusOne);
        managerDataOverviewDtlVo.setDataOverviewNoticeDtlVoS(dataOverviewNoticeDtlVo);

        return R.ok(managerDataOverviewDtlVo);
    }

}
