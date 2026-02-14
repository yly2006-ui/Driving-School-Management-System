package com.mashang.mashangdriving.controller.student;

import com.mashang.mashangdriving.domain.vo.student.StudentDataOverviewDtlVo;
import com.mashang.mashangdriving.domain.vo.student.StudentDataOverviewNoticeDtlVo;
import com.mashang.mashangdriving.service.manager.INoticeService;
import com.mashang.mashangdriving.service.student.IStudentService;
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
    public R studentDataOverview(Long studentId) {

        List<StudentDataOverviewNoticeDtlVo> studentDataOverviewNoticeDtlVos = noticeService.allDataOverviewNotice(studentId);
        StudentDataOverviewDtlVo student = studentService.student(studentId);
        student.setDataOverviewNoticeDtlVoS(studentDataOverviewNoticeDtlVos);

        if (student!=null) {
            return R.ok(student);
        }
        return R.fail("查询失败");


    }

}
