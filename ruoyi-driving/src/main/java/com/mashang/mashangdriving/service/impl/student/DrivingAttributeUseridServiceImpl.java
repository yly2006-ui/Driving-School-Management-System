package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingAttributeUserid;
import com.mashang.mashangdriving.mapper.student.DrivingAttributeUseridMapper;
import com.mashang.mashangdriving.service.student.IDrivingAttributeUseridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrivingAttributeUseridServiceImpl extends ServiceImpl<DrivingAttributeUseridMapper, DrivingAttributeUserid> implements IDrivingAttributeUseridService {
   @Autowired
   private DrivingAttributeUseridMapper drivingAttributeUseridMapper;

    @Override
    public Long selectAttributeId(Long contentId, Long userId) {
        return drivingAttributeUseridMapper.selectAttributeId(contentId,userId);
    }

    @Override
    public Long selectFinishedCourse(Long userId) {
        return drivingAttributeUseridMapper.selectFinishedCourse(userId);
    }

    @Override
    public Long selectCourseTotal() {
        return drivingAttributeUseridMapper.selectCourseTotal();
    }
}
