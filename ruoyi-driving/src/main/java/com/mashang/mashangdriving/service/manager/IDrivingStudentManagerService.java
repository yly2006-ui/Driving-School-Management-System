package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.Exception.BusinessException;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingStudentCreate;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingStudentQuery;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingStudentManagerUpdate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo;

public interface IDrivingStudentManagerService extends IService<DrivingStudent> {

    Page<DrivingStudentListVo> getList( Page<DrivingStudentListVo> page);

    DrivingStudentListVo selectOne( DrivingStudentQuery drivingStudentQuery) throws BusinessException;

    DrivingStudentListVo insertStudent(DrivingStudentCreate drivingStudentCreate);

    DrivingStudentListVo updateStudent(DrivingStudentManagerUpdate drivingStudentManagerUpdate);

    int deleteById( Long studentId);

}
