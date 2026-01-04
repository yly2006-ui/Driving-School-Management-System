package com.mashang.mashangdriving.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingCourseRecord;

public interface IDrivingCourseRecordService extends IService<DrivingCourseRecord> {
    //新增记录表
    void insertRecord(Long attributeId, Long userId);


}
