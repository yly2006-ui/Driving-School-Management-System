package com.mashang.mashangdriving.controller.student;

import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.param.student.update.DrivingStudentUpdate;
import com.mashang.mashangdriving.domain.vo.student.DrivingStudentDtlVo;
import com.mashang.mashangdriving.mapping.student.DrivingStudentMapping;
import com.mashang.mashangdriving.service.student.IDrivingStudentService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "学生端--个人信息")
@RestController
@RequestMapping("/drivingStudent")
public class DrivingStudentController extends BaseController {

    @Autowired
    private IDrivingStudentService drivingStudentService;

    @ApiOperation("查询个人信息详情")
    @GetMapping("/list/{studentId}")
    public R select(@PathVariable Long studentId){
        DrivingStudentDtlVo drivingStudentDtlVo = drivingStudentService.selectById(studentId);
        String selectMail = drivingStudentService.selectMail(SecurityUtils.getUserId());
        drivingStudentDtlVo.setEmail(selectMail);
        return R.ok(drivingStudentDtlVo);
    }

    @ApiOperation("修改个人信息")
    @PutMapping("/update")
    public  R update(@RequestBody DrivingStudentUpdate drivingStudentUpdate){
        DrivingStudent update = DrivingStudentMapping.INSTANCE.toUpdtae(drivingStudentUpdate);
        boolean b = drivingStudentService.updateById(update);
        return toR(b);
    }

    @ApiOperation("学员修改头像")
    @PostMapping("/updateAvatar")
    public R updateAvatar(
            @RequestParam("studentId") Long studentId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (file.isEmpty()) {
            return R.fail("头像文件不能为空");
        }
        String avatarUrl = drivingStudentService.updateAvatar(studentId, file);
        return R.ok("头像修改成功", avatarUrl);
    }
}