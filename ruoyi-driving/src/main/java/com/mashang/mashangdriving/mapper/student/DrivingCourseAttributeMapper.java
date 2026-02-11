package com.mashang.mashangdriving.mapper.student;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.DrivingCourseAttribute;
import com.mashang.mashangdriving.domain.vo.student.DrivingCourseAttributeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DrivingCourseAttributeMapper extends BaseMapper<DrivingCourseAttribute> {

    //查询学习端课程详情
    DrivingCourseAttributeVO selectByCourseId(@Param("attributeId")Long attributeId);

    //查询学习端课程学习情况
    String selectFinished(@Param("contentId")Long contentId,
                                                    @Param("userId")Long userId);

    //查询学习端课程完成数量
    List<DrivingCourseAttributeVO> countFinish(@Param("attributeId")Long attributeId,
                                               @Param("userId")Long userId);

    //查询课程下的小节数量
    Long  total(@Param("attributeId")Long attributeId);

    //查询学习端课程学习人数
    String selectStudyTotal(@Param("attributeId")Long attributeId);
}
