package com.mashang.mashangdriving.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mashang.mashangdriving.domain.entity.DrivingContent;
import com.mashang.mashangdriving.domain.param.student.create.DrivingContentCreate;
import com.mashang.mashangdriving.domain.param.student.update.DrivingContentUpdate;
import com.mashang.mashangdriving.mapping.student.DrivingContentMapping;
import com.mashang.mashangdriving.service.student.IDrivingContentService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "学员端--学习小节管理")
@RestController
@RequestMapping("/drivingContent")
public class DrivingContentController extends BaseController {

    @Autowired
    private IDrivingContentService drivingContentService;

    @ApiOperation("新增学习小节")
    @PostMapping("/save")
    public R insert(@RequestBody DrivingContentCreate create){
        int i = drivingContentService.insertContent(create);
        return toR(i);
    }

    @ApiOperation("修改学习小节")
    @PutMapping("/update")
    public R update(@RequestBody DrivingContentUpdate drivingContentUpdate){
        LambdaQueryWrapper<DrivingContent> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DrivingContent::getContectName,drivingContentUpdate.getContectName());
        lambdaQueryWrapper.ne(DrivingContent::getContentId,drivingContentUpdate.getContentId());
        DrivingContent one = drivingContentService.getOne(lambdaQueryWrapper);
        if (one!=null) {
            throw new RuntimeException("已存在此名称小节");
        }
        boolean save = drivingContentService.updateById(DrivingContentMapping.INSTANCE.toUpdate(drivingContentUpdate));
        return toR(save);
    }


    @ApiOperation("删除学习小节")
    @DeleteMapping("/delete/{contentId}")
    public R delete(@PathVariable Long contentId){
        boolean b = drivingContentService.removeById(contentId);
        return toR(b);

    }

}
