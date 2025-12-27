package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.DrivingCourse;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCourseDtlVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DrivingCourseMapper extends BaseMapper<DrivingCourse> {
    List<DrivingCourseDtlVo> selectByCourseId(@Param("courseId")Long courseId);
}
