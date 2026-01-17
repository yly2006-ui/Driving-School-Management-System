package com.mashang.mashangdriving.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingCourseAttribute;
import com.mashang.mashangdriving.domain.param.student.create.DrivingCourseAttributeCreate;
import com.mashang.mashangdriving.domain.param.student.update.DrivingCourseAttributeUpdate;
import com.mashang.mashangdriving.domain.vo.student.DrivingCourseAttributeVO;
import com.mashang.mashangdriving.domain.vo.student.DrivingCourseStudentListVo;
import com.mashang.mashangdriving.mapping.student.DrivingCourseAttributeMapping;
import com.mashang.mashangdriving.service.student.IDrivingCourseAttributeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @GetMapping("/Dtl/{attributeId}")
    @ApiOperation("查询学习资料详情")
    public R selectById(@PathVariable Long attributeId ){
        List<DrivingCourseAttributeVO> drivingCourseAttributeVOS = drivingCourseAttributeService.
                selectByCourseId(attributeId, SecurityUtils.getUserId());

        if (drivingCourseAttributeVOS != null) {
            return R.ok(drivingCourseAttributeVOS);
        } else {
            return R.fail();
        }
    }

    @ApiOperation("分页查询学习资料")
    @GetMapping("/list")
    public TableDataInfo<List<DrivingCourseStudentListVo>> list(@Validated PageQuery pageQuery, String name){
        Page<DrivingCourseAttribute> page =new Page<>(pageQuery.getPageNum(),pageQuery.getPageSize());
        LambdaQueryWrapper<DrivingCourseAttribute>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name),DrivingCourseAttribute::getAttributeName,name);
        Page<DrivingCourseAttribute> attributePage = drivingCourseAttributeService.page(page, lambdaQueryWrapper);

        List<DrivingCourseAttribute> records = attributePage.getRecords();
        List<DrivingCourseStudentListVo> drivingCourseAttributeVOS=new ArrayList<>();
            for (DrivingCourseAttribute record : records) {
                DrivingCourseStudentListVo drivingCourseStudentListVo=new DrivingCourseStudentListVo();
                Integer attributeId = Math.toIntExact(record.getAttributeId());
                String attributeName = record.getAttributeName();
                String introduction = record.getIntroduction();
                drivingCourseStudentListVo.setAttributeId(attributeId);
                drivingCourseStudentListVo.setAttributeName(attributeName);
                drivingCourseStudentListVo.setIntroduction(introduction);
                drivingCourseAttributeVOS.add(drivingCourseStudentListVo);

        }
        return getDataTable(drivingCourseAttributeVOS,attributePage.getTotal());
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
