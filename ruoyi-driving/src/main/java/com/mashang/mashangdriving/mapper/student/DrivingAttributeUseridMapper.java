package com.mashang.mashangdriving.mapper.student;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.DrivingAttributeUserid;

public interface DrivingAttributeUseridMapper extends BaseMapper<DrivingAttributeUserid> {


    //查询课程id
    Long selectAttributeId(Long  contentId,Long userId);

    //查询完成课程数
    Long selectFinishedCourse(Long userId);

    //查询课程数量
    Long selectCourseTotal();
}
