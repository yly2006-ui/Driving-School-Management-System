package com.mashang.mashangdriving.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingChapter;
import com.mashang.mashangdriving.domain.entity.DrivingCourse;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingChapterCreate;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingCourseCreate;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingSectionCreate;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingCourseQuery;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingChapterUpdate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingCourseUpdate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingSectionUpdate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCourseDtlVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCourseListVo;
import com.mashang.mashangdriving.mapping.manager.DrivingChapterMapping;
import com.mashang.mashangdriving.mapping.manager.DrivingCourseMapping;
import com.mashang.mashangdriving.service.manager.IDrivingChapterService;
import com.mashang.mashangdriving.service.manager.IDrivingCourseService;
import com.mashang.mashangdriving.service.manager.IDrivingSectionService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "管理端--课程管理")
@RestController
@RequestMapping("/drivingCourse")
public class DrivingCourseController extends BaseController {

    @Autowired
    private IDrivingCourseService drivingCourseService;

    @Autowired
    private IDrivingChapterService drivingChapterService;

    @Autowired
    private IDrivingSectionService drivingSectionService;

    @GetMapping("/list")
    @ApiOperation("分页查询课程列表")
    public TableDataInfo<List<DrivingCourseListVo>> list(@Validated PageQuery pageQuery,
                                                         DrivingCourseQuery drivingCourseQuery) {
        Page<DrivingCourseListVo> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        QueryWrapper<DrivingCourse> lqw = new QueryWrapper<>();
        lqw.like(StringUtils.isNotEmpty(drivingCourseQuery.getCourseName()), "c.course_name",
                drivingCourseQuery.getCourseName());
        lqw.eq(StringUtils.isNotEmpty(drivingCourseQuery.getType()),
               "c.type", drivingCourseQuery.getType());
        lqw.eq(StringUtils.isNotEmpty(drivingCourseQuery.getStatus()),
                "c.status", drivingCourseQuery.getStatus());
        lqw.eq("c.del_flag",0);
        lqw.orderByDesc("c.course_id");

        Page<DrivingCourseListVo> result = drivingCourseService.query(page, lqw);
        return getDataTable(result.getRecords(), result.getTotal());
    }

    @ApiOperation("查询课程详情")
    @GetMapping("/dtl/{courseId}")
    public R selectById(@PathVariable Long courseId) {
        DrivingCourseDtlVo drivingCourseDtlVos = drivingCourseService.selectByCourseId(courseId);
        if (drivingCourseDtlVos != null) {
            return R.ok(drivingCourseDtlVos);
        } else {
            return R.fail();
        }
    }

    @ApiOperation("新增课程")
    @PostMapping("/createCourse")
    public R createCourse(@RequestBody DrivingCourseCreate drivingCourseCreate) {
        DrivingCourse create = DrivingCourseMapping.INSTANCE.toCreate(drivingCourseCreate);
        LambdaQueryWrapper<DrivingCourse> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotEmpty(drivingCourseCreate.getCourseName()),
                DrivingCourse::getCourseName, drivingCourseCreate.getCourseName());
        long count = drivingCourseService.count(lqw);
        if (count > 0) {
            return R.fail("已存在此课程名称");
        }
        boolean save = drivingCourseService.save(create);

        return toR(save);
    }

    @ApiOperation("修改课程")
    @PutMapping("/updateCourse")
    public R updateCourse(@RequestBody DrivingCourseUpdate drivingCourseUpdate) {
        DrivingCourse update = DrivingCourseMapping.INSTANCE.toUpdate(drivingCourseUpdate);
        LambdaQueryWrapper<DrivingCourse> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotEmpty(drivingCourseUpdate.getCourseName()),
                DrivingCourse::getCourseName, drivingCourseUpdate.getCourseName());
        lqw.ne(DrivingCourse::getCourseId, drivingCourseUpdate.getCourseId());
        long count = drivingCourseService.count(lqw);
        if (count > 0) {
            return R.fail("已存在此课程名称");
        }
        boolean b = drivingCourseService.updateById(update);
        return toR(b);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("deleteCourseId/{courseId}")
    public R deleteCourse(@PathVariable Long courseId) {
        boolean b = drivingCourseService.removeById(courseId);
        return toR(b);
    }


    @ApiOperation("统计理论课程")
    @GetMapping("/theoreticalCourses/count")
    public R theoreticalCourses(){
        LambdaQueryWrapper<DrivingCourse>theoreticalCourses=new LambdaQueryWrapper<>();
        theoreticalCourses.eq(DrivingCourse::getType,"0");
        long count = drivingCourseService.count(theoreticalCourses);
        if (count>0){
            System.out.println("查询到的理论数量为"+count);
            return R.ok(count);
        }else {
            return R.fail();
        }}

        @ApiOperation("统计实操课程")
        @GetMapping("/practicalCourses/count")
        public R practicalCourses() {
            LambdaQueryWrapper<DrivingCourse> practicalCourses = new LambdaQueryWrapper<>();
            practicalCourses.eq(DrivingCourse::getType, "1");
            long count = drivingCourseService.count(practicalCourses);
            if (count > 0) {
                System.out.println("查询到的实操数量为" + count);
                return R.ok(count);
            } else {
                return R.fail();
            }
        }

    @ApiOperation("统计进行中课程")
    @GetMapping("/ingCourses/count")
    public R ingCourses() {
        LambdaQueryWrapper<DrivingCourse> ingCourses = new LambdaQueryWrapper<>();
        ingCourses.eq(DrivingCourse::getStatus, "0");
        long count = drivingCourseService.count(ingCourses);
        if (count > 0) {
            System.out.println("查询到的进行中的课程数量为" + count);
            return R.ok(count);
        } else {
            return R.fail();
        }
    }
    @ApiOperation("统计已结束课程")
    @GetMapping("/overCourses/count")
    public R overCourses() {
        LambdaQueryWrapper<DrivingCourse> overCourses = new LambdaQueryWrapper<>();
        overCourses.eq(DrivingCourse::getStatus, "2");
        long count = drivingCourseService.count(overCourses);
        if (count > 0) {
            System.out.println("查询到的结束的课程数量为" + count);
            return R.ok(count);
        } else {
            return R.fail();
        }
    }




    @ApiOperation("新增章")
    @PostMapping("/createChapter")
    public R createChapter(@RequestBody DrivingChapterCreate drivingChapterCreate) {
        DrivingChapter create = DrivingChapterMapping.INSTANCE.toCreate(drivingChapterCreate);
        LambdaQueryWrapper<DrivingChapter> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotEmpty(drivingChapterCreate.getChapterName()),
                DrivingChapter::getChapterName, drivingChapterCreate.getChapterName());
        long count = drivingChapterService.count(lqw);
        if (count > 0) {
            return R.fail("已存在此课程名称");
        }
        boolean save = drivingChapterService.save(create);

        return toR(save);
    }

    @ApiOperation("修改章")
    @PutMapping("/updateChapter")
    public R updateChapter(@RequestBody DrivingChapterUpdate drivingChapterUpdate) {
        DrivingChapter update = DrivingChapterMapping.INSTANCE.toUpdate(drivingChapterUpdate);

        LambdaQueryWrapper<DrivingChapter> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotEmpty(drivingChapterUpdate.getChapterName()),
                DrivingChapter::getChapterName, drivingChapterUpdate.getChapterName());
        lqw.eq(StringUtils.isNotNull(drivingChapterUpdate.getCourseId()),
                DrivingChapter::getCourseId, drivingChapterUpdate.getCourseId());
        lqw.ne(StringUtils.isNotNull(drivingChapterUpdate.getChapterId()),
                DrivingChapter::getChapterId, drivingChapterUpdate.getChapterId());

        long count = drivingChapterService.count(lqw);
        if (count > 0) {
            return R.fail("该课程下已存在此章名称");
        }

        boolean b = drivingChapterService.updateById(update);
        return toR(b);
    }

    @ApiOperation("删除章")
    @DeleteMapping("/deleteChapterId/{chapterId}")
    public R deleteChapter(@PathVariable Long chapterId) {
        boolean b = drivingChapterService.removeById(chapterId);
        return toR(b);
    }


    @ApiOperation("新增小节")
    @PostMapping("/createSection")
    public R createSection(@RequestBody DrivingSectionCreate drivingSectionCreate) {

        int save = drivingSectionService.insertSection(drivingSectionCreate);

        return toR(save);
    }

    @ApiOperation("修改小节")
    @PostMapping("/updateSection")
    public R createSection(@RequestBody DrivingSectionUpdate drivingSectionUpdate) {

        int updated = drivingSectionService.updateSection(drivingSectionUpdate);

        return toR(updated);
    }

    @ApiOperation("删除小节")
    @DeleteMapping("deleteSectionId/{sectionId}")
    public R removeSection(@PathVariable Long sectionId){
        boolean b = drivingCourseService.removeById(sectionId);
        return  toR(b);
    }
}
