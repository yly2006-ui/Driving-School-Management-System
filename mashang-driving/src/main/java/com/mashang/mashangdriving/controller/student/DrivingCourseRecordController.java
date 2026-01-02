package com.mashang.mashangdriving.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mashang.mashangdriving.domain.entity.DrivingContent;
import com.mashang.mashangdriving.domain.entity.DrivingCourseRecord;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingCourseQuery;
import com.mashang.mashangdriving.domain.param.student.update.DrivingCourseRecordQuery;
import com.mashang.mashangdriving.service.student.IDrivingContentService;
import com.mashang.mashangdriving.service.student.IDrivingCourseRecordService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
@Api(tags = "学生学习记录管理")
@RestController
@RequestMapping("/drivingCourseRecord")
public class DrivingCourseRecordController extends BaseController {

    @Autowired
    private IDrivingCourseRecordService drivingCourseRecordService;
    @Autowired
    private IDrivingContentService drivingContentService;

    @ApiOperation("新增学习记录")
    @PostMapping("/CourseRecord/save")
    public R saveRecord(@RequestParam Long ContentId){
        LambdaQueryWrapper<DrivingCourseRecord>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DrivingCourseRecord::getUserId, SecurityUtils.getUserId());
        lambdaQueryWrapper.eq(DrivingCourseRecord::getContentId,ContentId);
        DrivingCourseRecord one = drivingCourseRecordService.getOne(lambdaQueryWrapper);
        if (one==null){
            DrivingCourseRecord drivingCourseRecord=new DrivingCourseRecord();
            drivingCourseRecord.setContentId(ContentId);
            drivingCourseRecord.setStatus("2");
            drivingCourseRecord.setDelFlag("0");
            drivingCourseRecord.setFinishedHours("0");
            drivingCourseRecord.setUserId(SecurityUtils.getUserId());
            boolean save = drivingCourseRecordService.save(drivingCourseRecord);
            return toR(save);
        }
        return R.fail("已经有观看记录了");
    }

    @ApiOperation("观看时长达标")
    @PutMapping("/CourseRecord/update")
    public R updateRecord(@RequestBody DrivingCourseRecordQuery drivingCourseRecordQuery){
        LambdaQueryWrapper<DrivingCourseRecord>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DrivingCourseRecord::getUserId, SecurityUtils.getUserId());
        lambdaQueryWrapper.eq(DrivingCourseRecord::getContentId,drivingCourseRecordQuery.getContentId());
        DrivingCourseRecord one = drivingCourseRecordService.getOne(lambdaQueryWrapper);
        one.setFinishedHours(drivingCourseRecordQuery.getViewTime());
        LambdaQueryWrapper<DrivingContent>drivingContentLambdaQueryWrapper=new LambdaQueryWrapper<>();
        drivingContentLambdaQueryWrapper.eq(DrivingContent::getContentId,drivingCourseRecordQuery.getContentId());
        DrivingContent content = drivingContentService.getOne(drivingContentLambdaQueryWrapper);

        String contentTime = content.getContentTime();
        String finishedHours = one.getFinishedHours();
        int i = Integer.parseInt(finishedHours);
        int i1 = Integer.parseInt(contentTime);
        if (i==i1){
            DrivingCourseRecord drivingCourseRecord=new DrivingCourseRecord();
            drivingCourseRecord.setRecordId(one.getRecordId());
            drivingCourseRecord.setContentId(drivingCourseRecordQuery.getContentId());
            drivingCourseRecord.setStatus("0");
            drivingCourseRecord.setDelFlag("0");
            drivingCourseRecord.setFinishedHours(drivingCourseRecordQuery.getViewTime());
            drivingCourseRecord.setUserId(SecurityUtils.getUserId());
            boolean b = drivingCourseRecordService.updateById(drivingCourseRecord);
            return toR(b);
        }if (i<i1){
            return R.fail("还未观看达标");

        }


        return null;
    }

}
