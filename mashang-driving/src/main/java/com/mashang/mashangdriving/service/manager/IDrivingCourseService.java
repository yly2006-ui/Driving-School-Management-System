package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingCourse;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCourseDtlVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IDrivingCourseService extends IService<DrivingCourse> {
    List<DrivingCourseDtlVo> selectByCourseId(@Param("courseId")Long courseId);
}
