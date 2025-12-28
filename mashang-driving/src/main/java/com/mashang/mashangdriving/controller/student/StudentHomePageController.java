package com.mashang.mashangdriving.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mashang.mashangdriving.domain.entity.DrivingNotice;
import com.mashang.mashangdriving.domain.vo.manager.DataOverviewNoticeDtlVo;
import com.mashang.mashangdriving.domain.vo.manager.ManagerDataOverviewDtlVo;
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
public class StudentHomePageController extends BaseController {

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private IStudentService studentService;

    @ApiOperation("学员端----数据概览")
    @GetMapping("/student/overview")
    public R<StudentDataOverviewDtlVo> studentDataOverview(){

        List<StudentDataOverviewNoticeDtlVo> studentDataOverviewNoticeDtlVos = noticeService.allDataOverviewNotice();
        StudentDataOverviewDtlVo student = studentService.student();
        student.setDataOverviewNoticeDtlVoS(studentDataOverviewNoticeDtlVos);

        return R.ok(student);

    }

}
