package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingStudentQuery;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo;
import org.apache.ibatis.annotations.Param;

public interface DrivingStudentManagerMapper extends BaseMapper<DrivingStudent> {
    Page<DrivingStudentListVo> getList( Page<DrivingStudentListVo> page);
    DrivingStudentListVo selectOne(@Param("drivingStudentQuery") DrivingStudentQuery drivingStudentQuery);

}
