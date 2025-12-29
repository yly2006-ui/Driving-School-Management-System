package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingStudentQuery;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo;
import com.ruoyi.common.core.page.PageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface DrivingStudentManagerMapper extends BaseMapper<DrivingStudent> {
    Page<DrivingStudentListVo> getList(@Param("drivingStudent")DrivingStudent drivingStudent, Page<DrivingStudentListVo> page);
    DrivingStudentListVo selectOne(@Param("drivingStudentQuery") DrivingStudentQuery drivingStudentQuery);
}
