package com.mashang.mashangdriving.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingCourseAttribute;
import com.mashang.mashangdriving.domain.entity.DrivingCourseAttributeRecord;
import com.mashang.mashangdriving.domain.param.student.create.DrivingCourseAttributeCreate;
import com.mashang.mashangdriving.domain.param.student.update.DrivingCourseAttributeUpdate;
import com.mashang.mashangdriving.domain.vo.student.DrivingCourseAttributeVO;
import com.mashang.mashangdriving.domain.vo.student.DrivingCourseStudentListVo;
import com.mashang.mashangdriving.mapping.student.DrivingCourseAttributeMapping;
import com.mashang.mashangdriving.service.student.IDrivingCourseAttributeRecordService;
import com.mashang.mashangdriving.service.student.IDrivingCourseAttributeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "学员端--课程管理")
@RestController
@RequestMapping("/drivingCourseAttribute")
public class DrivingCourseAttributeController extends BaseController {

    @Autowired
    private IDrivingCourseAttributeService drivingCourseAttributeService;
    @Autowired
    private IDrivingCourseAttributeRecordService drivingCourseAttributeRecordService;

    @GetMapping("/Dtl/{attributeId}")
    @ApiOperation("查询学习资料详情")
    public R selectById(@PathVariable Long attributeId ){
        DrivingCourseAttributeVO drivingCourseAttributeVOS = drivingCourseAttributeService.
                selectByCourseId(attributeId, SecurityUtils.getUserId());

        LambdaQueryWrapper<DrivingCourseAttributeRecord>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DrivingCourseAttributeRecord::getCourseAttributeId,attributeId);
        lambdaQueryWrapper.eq(DrivingCourseAttributeRecord::getUserId,SecurityUtils.getUserId());
        DrivingCourseAttributeRecord one = drivingCourseAttributeRecordService.getOne(lambdaQueryWrapper);
        System.out.println(one);
        if (one==null){
        DrivingCourseAttributeRecord record =new DrivingCourseAttributeRecord();
        record.setCourseAttributeId(attributeId);
        record.setUserId(SecurityUtils.getUserId());
        boolean save = drivingCourseAttributeRecordService.save(record);
        if (!save){
            throw new RuntimeException("插入课程人数记录表失败");
        }}
        if (drivingCourseAttributeVOS != null) {
            return R.ok(drivingCourseAttributeVOS);
        } else {
            return R.fail();
        }
    }

    @ApiOperation("查询学习资料列表")
    @GetMapping("/list")
    public TableDataInfo<List<DrivingCourseStudentListVo>> list(@ApiParam("课程名称") String name){
        LambdaQueryWrapper<DrivingCourseAttribute>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name),DrivingCourseAttribute::getAttributeName,name);
        List<DrivingCourseAttribute> list = drivingCourseAttributeService.list(lambdaQueryWrapper);
        List<DrivingCourseStudentListVo> vos = DrivingCourseAttributeMapping.INSTANCE.toList(list);
        for (DrivingCourseStudentListVo vo : vos) {
            Integer attributeId = vo.getAttributeId();
            DrivingCourseAttributeVO drivingCourseAttributeVO = drivingCourseAttributeService.
                    selectByCourseId(Long.valueOf(attributeId), SecurityUtils.getUserId());
                String percentage = drivingCourseAttributeVO.getPercentage();
                String courseCount = drivingCourseAttributeVO.getCourseCount();
                String studyPersonTotal = drivingCourseAttributeVO.getStudyPersonTotal();
                String totalTime = drivingCourseAttributeVO.getTotalTime();
                vo.setPercentage(percentage);
                vo.setCourseCount(courseCount);
                vo.setStudyPersonTotal(studyPersonTotal);
                vo.setTotalTime(totalTime);
        }


        return getDataTable(vos);
    }

    @ApiOperation("新增学习课程")
    @PostMapping("/save")
    public R insert(@RequestBody DrivingCourseAttributeCreate attributeCreate){
        LambdaQueryWrapper<DrivingCourseAttribute>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DrivingCourseAttribute::getAttributeName,attributeCreate.getAttributeName());
        DrivingCourseAttribute one = drivingCourseAttributeService.getOne(lambdaQueryWrapper);
        if (one!=null) {
            throw new RuntimeException("已存在此名称课程");
        }
        boolean save = drivingCourseAttributeService.save(DrivingCourseAttributeMapping.INSTANCE.toCreate(attributeCreate));
        return toR(save);
    }

    @ApiOperation("修改学习课程")
    @PutMapping("/update")
    public R update(@RequestBody DrivingCourseAttributeUpdate attributeUpdate){
        LambdaQueryWrapper<DrivingCourseAttribute>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DrivingCourseAttribute::getAttributeName,attributeUpdate.getAttributeName());
        lambdaQueryWrapper.ne(DrivingCourseAttribute::getAttributeId,attributeUpdate.getAttributeId());
        DrivingCourseAttribute one = drivingCourseAttributeService.getOne(lambdaQueryWrapper);
        if (one!=null) {
            throw new RuntimeException("已存在此名称课程");
        }
        boolean save = drivingCourseAttributeService.updateById(DrivingCourseAttributeMapping.INSTANCE.toUpdate(attributeUpdate));
        return toR(save);
    }

    @ApiOperation("删除学习课程")
    @DeleteMapping("/delete/{attributeId}")
    public R delete(@PathVariable Long attributeId){
        boolean b = drivingCourseAttributeService.removeById(attributeId);
        return toR(b);

    }
}
