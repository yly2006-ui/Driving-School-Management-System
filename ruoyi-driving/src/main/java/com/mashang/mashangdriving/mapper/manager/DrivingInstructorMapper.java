package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.CoachWeeklySchedule;
import com.mashang.mashangdriving.domain.entity.DrivingInstructor;
import com.mashang.mashangdriving.domain.entity.DrivingInstructorStatus;
import com.mashang.mashangdriving.domain.vo.manager.DrivingInstructorListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingOneInstructorVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface DrivingInstructorMapper extends BaseMapper<DrivingInstructor> {
    Page<DrivingInstructorListVo> getList(Page<DrivingInstructorListVo> page);

    DrivingOneInstructorVo getByInstructorId(@Param("instructorId") Long instructorId);
    Page<DrivingOneInstructorVo> getByInstructorName(@Param("instructorName") String instructorName,Page<DrivingOneInstructorVo> page);


    List<Map<String,Object>> findWeeklyScheduleByInstructorId(@Param("instructorId")Long instructorId);
    DrivingInstructorStatus getAllStatus();


    int batchUpsert(@Param("list") List<CoachWeeklySchedule> schedules);

}
