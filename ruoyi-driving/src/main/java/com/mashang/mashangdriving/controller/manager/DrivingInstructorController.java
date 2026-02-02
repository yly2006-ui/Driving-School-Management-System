package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingInstructorCreate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingInstructorUpdate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingScheduleUpdateDTO;
import com.mashang.mashangdriving.domain.vo.manager.DrivingInstructorListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingOneInstructorVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingRatingStudentVO;
import com.mashang.mashangdriving.service.manager.IDrivingInstructorService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "管理端--教练管理")
@RequestMapping("/instructorManager")
public class DrivingInstructorController extends BaseController {

    @Autowired
    private IDrivingInstructorService drivingInstructorService;

    @GetMapping("/list")
    @ApiOperation("分页查询教练列表")
    public TableDataInfo<DrivingInstructorListVo> list(PageQuery pageQuery) {
        Page<DrivingInstructorListVo> drivingInstructorListVoPage = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        Page<DrivingInstructorListVo> list = drivingInstructorService.getList(drivingInstructorListVoPage);
        return getDataTable(list.getRecords(),list.getTotal());
    }

    @GetMapping("/selectOne")
    @ApiOperation("查询教练详细信息")
    public R selectOne(@RequestParam("instructorId") Long instructorId) {
        DrivingOneInstructorVo instructor = drivingInstructorService.getByInstructorId(instructorId);
        if (instructor == null)  {
            return R.fail("该用户不存在");
        }
        return R.ok(instructor);
    }
    @GetMapping("/selectInstructor")
    @ApiOperation("搜索教练")
    public TableDataInfo<DrivingOneInstructorVo> selectInstructor(@RequestParam("instructorName") String instructorName,PageQuery pageQuery) {
        Page<DrivingOneInstructorVo> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        Page<DrivingOneInstructorVo> instructor = drivingInstructorService.getByInstructorName(instructorName,page);
        if (instructor == null)  {
            return new TableDataInfo<>();
        }
        return getDataTable(instructor.getRecords(),instructor.getTotal());
    }

    @GetMapping("/selectAllStatus")
    @ApiOperation("查看所有教练状态")
    public R selectAllStatus() {
        return R.ok(drivingInstructorService.getAllStatus());
    }


    @GetMapping("/getRating")
    @ApiOperation("查看教练评论")
     public TableDataInfo<DrivingRatingStudentVO> getRating(@RequestParam("instructorId") Long instructorId, PageQuery pageQuery,
                                                            @RequestParam(value = "timeFilter",required = false,defaultValue = "all")
                                                            @ApiParam(value = "时间筛选: all(全部时间), week(最近一周), month(最近一月), quarter(最近三月)",
                                                                    allowableValues = "all,week,month,quarter",
                                                                    example = "week")String timeFilter) {
        Page<DrivingRatingStudentVO> drivingRatingPage = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        Page<DrivingRatingStudentVO> rating = drivingInstructorService.getRatingByInstructorWithStudentInfo(instructorId,drivingRatingPage,timeFilter);
        if (rating == null)  {
            return new TableDataInfo<>();
        }
        return getDataTable(rating.getRecords(),rating.getTotal());
    }


    @PostMapping("/createInstructor")
    @ApiOperation("新增教练")
    public R insert( DrivingInstructorCreate drivingInstructorCreate) {
        DrivingInstructorListVo insert = drivingInstructorService.insert(drivingInstructorCreate);
        if (insert == null) {
            return R.fail("新增失败");
        }
        return R.ok(insert);
    }
    @PutMapping("/updateInstructor")
    @ApiOperation("修改教练")
    public R update( DrivingInstructorUpdate drivingInstructorUpdate) {
        DrivingInstructorListVo update = drivingInstructorService.update(drivingInstructorUpdate);
        if (update == null) {
            return R.fail("修改失败");
        }
        return R.ok(update);
    }

    @ApiOperation("查询时段安排表")
    @GetMapping("/matrix/with-week")
    public Map<String, Object> getScheduleMatrixWithWeek(@RequestParam("instructorId") Long instructorId) {
        int[][] matrix = drivingInstructorService.createScheduleMatrixFromDB(instructorId);

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

    @ApiOperation("更新部分时段安排")
    @PostMapping("/updatePartialSchedule")
    public R updatePartialSchedule(  DrivingScheduleUpdateDTO dto) {
        try {
            // 参数验证
            if (dto.getInstructorId() == null || dto.getInstructorId() <= 0) {
                return R.fail("教练ID无效");
            }
            List<Integer> weekDayList = dto.getWeekDayList();
            for (Integer weekDay : weekDayList) {
                if(weekDay == null||weekDay<=0||weekDay>=7){
                    return R.fail("weekDay必须在1-7内"+weekDay+"不在范围内");
                }
            }
                if (dto.getStatus() == null || (dto.getStatus() != -1 && dto.getStatus() != 0 && dto.getStatus() != 1)) {
                    return R.fail("状态参数必须为-1、0或1");
                }
            List<Integer> timeSlotList = dto.getTimeSlotList();
            for (Integer timeSlot : timeSlotList) {
                if (timeSlot == null || timeSlot < 0 || timeSlot >= 19) {
                    return R.fail("时间段索引必须为0-18，无效值: " + timeSlot);
                }
            }
            drivingInstructorService.updatePartialSchedule(dto);
            return R.ok("部分排班更新成功，共更新" + dto.getTimeSlotList().size() + "个时段");

        } catch (Exception e) {
            return R.fail("更新失败: " + e.getMessage());
        }

}}
