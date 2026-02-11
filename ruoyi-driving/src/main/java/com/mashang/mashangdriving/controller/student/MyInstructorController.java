package com.mashang.mashangdriving.controller.student;

import com.mashang.mashangdriving.domain.vo.student.MyInstructorVo;
import com.mashang.mashangdriving.service.student.IMyInstructorService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/myInstructor")
@Api(tags = "学员端--我的教练")
public class MyInstructorController extends BaseController {
    @Autowired
    private IMyInstructorService myInstructorService;

    @GetMapping
    @ApiOperation("查询我的教练")
    public R getMyInstructor(Long studentId) {
        MyInstructorVo myInstructorVo = myInstructorService.selectMyInstructorById(studentId);
        if (myInstructorVo == null) {
            return R.fail("该学员没有相对应的教练");
        }
        return R.ok(myInstructorVo);
    }
    @ApiOperation("查询时段安排表")
    @GetMapping("/matrix/with-week")
    public Map<String, Object> getScheduleMatrixWithWeek(@RequestParam("studentId") Long studentId) {
        int[][] matrix = myInstructorService.createScheduleMatrixFromDB(studentId);

        Map<String, Object> result = new HashMap<>();
        result.put("scheduleMatrix", matrix);
        result.put("weekDays", new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"});
        result.put("timeSlots", new String[]{
                "04:00-05:00", "05:00-06:00", "06:00-07:00", "07:00-08:00",
                "08:00-09:00", "09:00-10:00", "10:00-11:00", "11:00-12:00",
                "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00",
                "16:00-17:00", "17:00-18:00", "18:00-19:00", "19:00-20:00",
                "20:00-21:00", "21:00-22:00", "22:00-23:00"
        });

        return result;
    }
}
