package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingCourse;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCourseDtlVo;
import com.mashang.mashangdriving.mapper.manager.DrivingCourseMapper;
import com.mashang.mashangdriving.service.manager.IDrivingCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrivingCourseServiceImpl extends ServiceImpl<DrivingCourseMapper, DrivingCourse> implements IDrivingCourseService {
    @Autowired
    private DrivingCourseMapper drivingCourseMapper;
    @Override
    public List<DrivingCourseDtlVo> selectByCourseId(Long courseId) {
        List<DrivingCourseDtlVo> drivingCourseDtlVos = drivingCourseMapper.selectByCourseId(courseId);
        return drivingCourseDtlVos;
    }
}
