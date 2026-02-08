package com.mashang.mashangdriving.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingCourseRecord;
import com.mashang.mashangdriving.domain.param.student.update.DrivingCourseRecordQuery;
import org.springframework.web.bind.annotation.RequestBody;

public interface IDrivingCourseRecordService extends IService<DrivingCourseRecord> {
//    //新增记录表
//    void insertRecord(Long attributeId, Long userId);

    //更新记录表
    int updateRecord( DrivingCourseRecordQuery drivingCourseRecordQuery);


}
