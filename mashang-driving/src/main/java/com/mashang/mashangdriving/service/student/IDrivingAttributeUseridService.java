package com.mashang.mashangdriving.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingAttributeUserid;

public interface IDrivingAttributeUseridService extends IService<DrivingAttributeUserid> {

    //查询课程id
    Long selectAttributeId(Long  contentId,Long userId);

    //查询完成课程数
    Long selectFinishedCourse(Long userId);

    //查询课程数量
    Long selectCourseTotal();
}
