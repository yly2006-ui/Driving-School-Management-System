package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingCourseRecord;
import com.mashang.mashangdriving.mapper.student.DrivingCourseRecordMapper;
import com.mashang.mashangdriving.service.student.IDrivingCourseRecordService;
import org.springframework.stereotype.Service;

@Service
public class DrivingCourseRecordServiceImpl extends ServiceImpl<DrivingCourseRecordMapper, DrivingCourseRecord> implements IDrivingCourseRecordService {
}
