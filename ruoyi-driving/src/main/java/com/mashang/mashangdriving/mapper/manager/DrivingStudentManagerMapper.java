package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingStudentQuery;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo1;
import org.apache.ibatis.annotations.Param;

public interface DrivingStudentManagerMapper extends BaseMapper<DrivingStudent> {
    Page<DrivingStudentListVo1> getList( Page<DrivingStudentListVo1> page);
    Page<DrivingStudentListVo1> selectOneStudent(@Param("drivingStudentQuery") DrivingStudentQuery drivingStudentQuery,Page<DrivingStudentListVo1> page);

}
