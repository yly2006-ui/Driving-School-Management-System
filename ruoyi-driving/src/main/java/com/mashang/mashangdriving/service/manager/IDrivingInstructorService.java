package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingInstructor;
import com.mashang.mashangdriving.domain.entity.DrivingInstructorStatus;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingInstructorCreate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingInstructorUpdate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingScheduleUpdateDTO;
import com.mashang.mashangdriving.domain.vo.manager.DrivingInstructorListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingOneInstructorVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingRatingStudentVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface IDrivingInstructorService extends IService<DrivingInstructor> {
    Page<DrivingInstructorListVo> getList(Page<DrivingInstructorListVo> page);
    DrivingOneInstructorVo getByInstructorId(@RequestParam("instructorId") Long instructorId);
    Page<DrivingOneInstructorVo> getByInstructorName(@RequestParam("instructorName") String instructorName,Page<DrivingOneInstructorVo> page);
    DrivingInstructorListVo insert(DrivingInstructorCreate drivingInstructorCreate);
    DrivingInstructorListVo update(DrivingInstructorUpdate drivingInstructorUpdate);
    Page<DrivingRatingStudentVO> getRatingByInstructorWithStudentInfo(
            @Param("instructorId") Long instructorId,
            Page<DrivingRatingStudentVO> page,String timeFilter,String scoreLevel);
    int[][] createScheduleMatrixFromDB(Long instructorId);

    DrivingInstructorStatus getAllStatus();



    void updatePartialSchedule(DrivingScheduleUpdateDTO dto);


}
