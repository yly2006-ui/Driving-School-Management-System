package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingCourse;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCourseDtlVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCourseListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IDrivingCourseService extends IService<DrivingCourse> {
    List<DrivingCourseDtlVo> selectByCourseId(@Param("courseId")Long courseId);

    //分页查询课程信息·
    Page<DrivingCourseListVo> query(@Param("page") Page<DrivingCourseListVo> page,
                                    @Param(Constants.WRAPPER) LambdaQueryWrapper<DrivingCourse> wrapper);
}
