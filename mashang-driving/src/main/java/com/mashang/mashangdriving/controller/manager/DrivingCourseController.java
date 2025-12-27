package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingCourse;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingCourseQuery;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingCourseUpdate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCourseCreate;
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
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
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
        lqw.orderByDesc(DrivingCourse::getCourseId);

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

    @ApiOperation("新增课程")
    @PostMapping("/create")
    public R createCourse(@RequestBody DrivingCourseCreate drivingCourseCreate){
        DrivingCourse create = DrivingCourseMapping.INSTANCE.toCreate(drivingCourseCreate);
        LambdaQueryWrapper<DrivingCourse>lqw=new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotEmpty(drivingCourseCreate.getCourseName()),
                DrivingCourse::getCourseName,drivingCourseCreate.getCourseName());
        long count = drivingCourseService.count(lqw);
        if (count>0){
            return R.fail("已存在此课程名称");
        }
        boolean save = drivingCourseService.save(create);

        return toR(save);
    }

    @ApiOperation("修改课程")
    @PutMapping("/update")
    public  R updateCourse(@RequestBody DrivingCourseUpdate drivingCourseUpdate){
        DrivingCourse update = DrivingCourseMapping.INSTANCE.toUpdate(drivingCourseUpdate);
        LambdaQueryWrapper<DrivingCourse>lqw=new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotEmpty(drivingCourseUpdate.getCourseName()),
                DrivingCourse::getCourseName,drivingCourseUpdate.getCourseName());
        lqw.ne(DrivingCourse::getCourseId,drivingCourseUpdate.getCourseId());
        long count = drivingCourseService.count(lqw);
        if (count>0){
            return R.fail("已存在此课程名称");
        }
        boolean b = drivingCourseService.updateById(update);
        return toR(b);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("delete/{courseId}")
    public  R deleteCourse(@PathVariable Long courseId){
        boolean b = drivingCourseService.removeById(courseId);
        return toR(b);
    }
}
