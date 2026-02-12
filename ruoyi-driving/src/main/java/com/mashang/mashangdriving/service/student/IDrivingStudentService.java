package com.mashang.mashangdriving.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.vo.student.DrivingStudentDtlVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IDrivingStudentService extends IService<DrivingStudent> {
    DrivingStudentDtlVo selectById(@Param("studentId") Long studentId);

    //查询学生对应邮箱
    String selectMail(Long userId);

    int uploadAvatar(Long studentId, MultipartFile file) throws IOException;
}
