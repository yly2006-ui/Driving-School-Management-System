package com.mashang.mashangdriving.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mashang.mashangdriving.domain.entity.DrivingContent;
import com.mashang.mashangdriving.domain.entity.DrivingCourseRecord;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.param.student.update.DrivingCourseRecordQuery;
import com.mashang.mashangdriving.service.student.IDrivingAttributeUseridService;
import com.mashang.mashangdriving.service.student.IDrivingContentService;
import com.mashang.mashangdriving.service.student.IDrivingCourseRecordService;
import com.mashang.mashangdriving.service.student.IDrivingStudentService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "学生学习记录管理")
@RestController
@RequestMapping("/drivingCourseRecord")
public class DrivingCourseRecordController extends BaseController {

    @Autowired
    private IDrivingCourseRecordService drivingCourseRecordService;
    @Autowired
    private IDrivingContentService drivingContentService;
    @Autowired
    private IDrivingStudentService drivingStudentService;
    @Autowired
    private IDrivingAttributeUseridService drivingAttributeUseridService;


    @ApiOperation("新增学习记录")
    @PostMapping("/CourseRecord/save")
    public R saveRecord(@RequestParam Long ContentId){
        LambdaQueryWrapper<DrivingCourseRecord>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DrivingCourseRecord::getUserId, SecurityUtils.getUserId());
        lambdaQueryWrapper.eq(DrivingCourseRecord::getContentId,ContentId);
        DrivingCourseRecord one = drivingCourseRecordService.getOne(lambdaQueryWrapper);

        LambdaQueryWrapper<DrivingStudent>studentLambdaQueryWrapper=new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(DrivingStudent::getUserId,SecurityUtils.getUserId());
        DrivingStudent Student = drivingStudentService.getOne(studentLambdaQueryWrapper);
        if (one==null){
            DrivingCourseRecord drivingCourseRecord=new DrivingCourseRecord();
            drivingCourseRecord.setContentId(ContentId);
            drivingCourseRecord.setStatus("2");
            drivingCourseRecord.setDelFlag("0");
            drivingCourseRecord.setFinishedHours("0");
            drivingCourseRecord.setUserId(SecurityUtils.getUserId());
            drivingCourseRecord.setStudentId(Student.getStudentId());
            boolean save = drivingCourseRecordService.save(drivingCourseRecord);
            return toR(save);
        }
        return R.fail("已经有观看记录了");
    }

    @ApiOperation("观看时长达标")
    @PutMapping("/CourseRecord/update")
    public R updateRecord(@RequestBody DrivingCourseRecordQuery drivingCourseRecordQuery){
        int i = drivingCourseRecordService.updateRecord(drivingCourseRecordQuery);
        return toR(i);
    }



}
