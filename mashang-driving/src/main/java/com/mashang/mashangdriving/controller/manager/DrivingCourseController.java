package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingCourse;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingCourseQuery;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCourseDtlVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCourseListVo;
import com.mashang.mashangdriving.mapping.manager.DrivingCourseMapping;
import com.mashang.mashangdriving.service.manager.IDrivingCourseService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Api(tags = "管理端--课程管理")
@RestController
@RequestMapping("/drivingCourse")
public class DrivingCourseController extends BaseController {

    @Autowired
    private IDrivingCourseService drivingCourseService;

    @GetMapping("/list")
    @ApiOperation("分页查询课程列表")
    public TableDataInfo<List<DrivingCourseListVo>> list(@Validated PageQuery pageQuery,
                                                         DrivingCourseQuery drivingCourseQuery){
        Page<DrivingCourse> page=new Page<>(pageQuery.getPageNum(),pageQuery.getPageSize());
        LambdaQueryWrapper<DrivingCourse> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotEmpty(drivingCourseQuery.getCourseName()),DrivingCourse::getCourseName,
                drivingCourseQuery.getCourseName());
        lqw.eq(StringUtils.isNotEmpty(drivingCourseQuery.getType()),
                DrivingCourse::getType,drivingCourseQuery.getType());
        lqw.eq(StringUtils.isNotEmpty(drivingCourseQuery.getStatus()),
                DrivingCourse::getStatus,drivingCourseQuery.getStatus());

        Page<DrivingCourse> result = drivingCourseService.page(page, lqw);
        List<DrivingCourseListVo> list = DrivingCourseMapping.INSTANCE.toList(result.getRecords());
        return getDataTable(list,result.getTotal());
    }

    @ApiOperation("查询课程详情")
    @GetMapping("/dtl/{courseId}")
    public R selectById(@PathVariable Long courseId){
        List<DrivingCourseDtlVo> drivingCourseDtlVos = drivingCourseService.selectByCourseId(courseId);
        if (drivingCourseDtlVos!=null){
            return R.ok(drivingCourseDtlVos);
        }else {
            return R.fail();
        }
    }

}
