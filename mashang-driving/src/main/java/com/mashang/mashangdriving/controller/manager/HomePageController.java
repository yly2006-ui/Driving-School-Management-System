package com.mashang.mashangdriving.controller.manager;

import com.mashang.mashangdriving.domain.vo.manager.DataOverviewDtlVo;
import com.mashang.mashangdriving.service.manager.IInstructorService;
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

@Api(tags = "首页")
@RestController
@RequestMapping("/home")
public class HomePageController extends BaseController {

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private IInstructorService instructorService;

    @Autowired
    private IStudentService studentService;

    @ApiOperation("管理端----数据概览")
    @GetMapping("/manager/overview")
    public R<DataOverviewDtlVo> DataOverview(){

        return R.ok();
    }




}
