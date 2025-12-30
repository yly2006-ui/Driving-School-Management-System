package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.Exception.BusinessException;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingStudentCreate;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingStudentQuery;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo;
import com.ruoyi.common.core.page.PageQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IDrivingStudentManagerService extends IService<DrivingStudent> {

    Page<DrivingStudentListVo> getList(DrivingStudent drivingStudent, Page<DrivingStudentListVo> page);

    DrivingStudentListVo selectOne( DrivingStudentQuery drivingStudentQuery) throws BusinessException;

    DrivingStudentListVo insertStudent(DrivingStudentCreate drivingStudentCreate);

}
