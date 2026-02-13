package com.mashang.mashangdriving.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mashang.mashangdriving.domain.entity.DrivingContent;
import com.mashang.mashangdriving.domain.entity.DrivingLable;
import com.mashang.mashangdriving.domain.param.student.create.DrivingLableCreate;
import com.mashang.mashangdriving.domain.param.student.update.DrivingLableUpdate;
import com.mashang.mashangdriving.mapping.student.DrivingLableMapping;
import com.mashang.mashangdriving.service.student.IDrivingContentService;
import com.mashang.mashangdriving.service.student.IDrivingLableService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "学员端--课程标签管理")
@RestController
@RequestMapping("/drivingLable")
public class DrivingLableController extends BaseController {

    @Autowired
    private IDrivingLableService drivingLableService;
    @Autowired
    private IDrivingContentService drivingContentService;

    @ApiOperation("新增学习标签")
    @PostMapping("/save")
    public R insert(@RequestBody DrivingLableCreate drivingLableCreate){
        LambdaQueryWrapper<DrivingLable> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DrivingLable::getLableName,drivingLableCreate.getLableName());
        DrivingLable one = drivingLableService.getOne(lambdaQueryWrapper);
        if (one!=null) {
            throw new RuntimeException("已存在此名称标签");
        }
        boolean save = drivingLableService.save(DrivingLableMapping.INSTANCE.toCreate(drivingLableCreate));
        return toR(save);
    }

    @ApiOperation("修改学习标签")
    @PutMapping("/update")
    public R update(@RequestBody DrivingLableUpdate lableUpdate){
        LambdaQueryWrapper<DrivingLable> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DrivingLable::getLableName,lableUpdate.getLableName());
        lambdaQueryWrapper.ne(DrivingLable::getLableId,lableUpdate.getLableId());
        DrivingLable one = drivingLableService.getOne(lambdaQueryWrapper);
        if (one!=null) {
            throw new RuntimeException("已存在此名称标签");
        }
        boolean save = drivingLableService.updateById(DrivingLableMapping.INSTANCE.toUpdate(lableUpdate));
        return toR(save);
    }


    @ApiOperation("删除学习标签")
    @DeleteMapping("/delete/{lableId}")
    public R delete(@PathVariable Long lableId){
        LambdaQueryWrapper<DrivingContent>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DrivingContent::getLableId,lableId);
        List<DrivingContent> drivingContent = drivingContentService.list(lambdaQueryWrapper);
        if (CollectionUtils.isNotEmpty(drivingContent)){return R.fail("此章之下存在小节");}

        boolean b = drivingLableService.removeById(lableId);
        return toR(b);

    }
}
