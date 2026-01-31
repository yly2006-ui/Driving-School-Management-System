package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.Exception.BusinessException;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingStudentCreate;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingStudentQuery;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingStudentManagerUpdate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo1;

public interface IDrivingStudentManagerService extends IService<DrivingStudent> {

    Page<DrivingStudentListVo> getList( Page<DrivingStudentListVo> page);

    Page<DrivingStudentListVo1> selectOne(DrivingStudentQuery drivingStudentQuery,Page<DrivingStudentListVo1> page);

    DrivingStudentListVo insertStudent(DrivingStudentCreate drivingStudentCreate);

    DrivingStudentListVo updateStudent(DrivingStudentManagerUpdate drivingStudentManagerUpdate);

    int deleteById( Long studentId);

}
