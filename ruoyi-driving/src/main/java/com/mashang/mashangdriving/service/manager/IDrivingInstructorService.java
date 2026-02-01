package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.CoachWeeklySchedule;
import com.mashang.mashangdriving.domain.entity.DrivingInstructor;
import com.mashang.mashangdriving.domain.entity.DrivingRating;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingInstructorCreate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingInstructorUpdate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingScheduleUpdateDTO;
import com.mashang.mashangdriving.domain.vo.manager.DrivingInstructorListVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IDrivingInstructorService extends IService<DrivingInstructor> {
    Page<DrivingInstructorListVo> getList(Page<DrivingInstructorListVo> page);
    DrivingInstructorListVo getByName(@RequestParam("instructorName") String name);
    DrivingInstructorListVo insert(DrivingInstructorCreate drivingInstructorCreate);
    DrivingInstructorListVo update(DrivingInstructorUpdate drivingInstructorUpdate);
    List<DrivingRating> getRating(@RequestParam("instructorId") Long instructorId);
    int[][] createScheduleMatrixFromDB(Long instructorId);


    void updatePartialSchedule(DrivingScheduleUpdateDTO dto);


}
