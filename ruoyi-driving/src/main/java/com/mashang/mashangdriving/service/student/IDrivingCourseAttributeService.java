package com.mashang.mashangdriving.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingCourseAttribute;
import com.mashang.mashangdriving.domain.vo.student.DrivingCourseAttributeVO;

import java.util.List;

public interface IDrivingCourseAttributeService extends IService<DrivingCourseAttribute> {

    //查询学习端课程详情
    List<DrivingCourseAttributeVO> selectByCourseId(Long attributeId,Long userId);
}
