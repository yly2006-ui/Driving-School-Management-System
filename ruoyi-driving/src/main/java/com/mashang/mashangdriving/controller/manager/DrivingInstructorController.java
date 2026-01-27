package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingRating;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingInstructorCreate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingInstructorUpdate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingInstructorDateVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingInstructorListVo;
import com.mashang.mashangdriving.service.manager.IDrivingInstructorService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public R selectOne(@RequestParam("instructorName") String instructorName) {
        DrivingInstructorListVo instructor = drivingInstructorService.getByName(instructorName);
        if (instructor == null)  {
            return R.fail("该用户不存在");
        }
        return R.ok(instructor);
    }
    @GetMapping("/getRating")
    @ApiOperation("查看教练评论")
     public R getRating(@RequestParam("instructorId") Long instructorId){
        List<DrivingRating> rating = drivingInstructorService.getRating(instructorId);
        if (rating == null)  {
            return R.fail("评论不存在");
        }
        return R.ok(rating);
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

    @GetMapping
    @ApiOperation("时段安排")
    public DrivingInstructorDateVo get(@RequestParam("instructorId") Long instructorId) {
        DrivingInstructorDateVo date = drivingInstructorService.getDate(instructorId);
        if (date == null) {
            throw new RuntimeException("查询失败");
        }
        return date;
    }


}
