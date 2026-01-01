package com.mashang.mashangdriving.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingSubject;
import com.mashang.mashangdriving.domain.vo.student.AllSubjectVo;
import com.mashang.mashangdriving.domain.vo.student.StudentDataOverviewDtlVo;

import java.util.List;

public interface ISubjectService extends IService<DrivingSubject> {

    List<AllSubjectVo> allSubject();
}
