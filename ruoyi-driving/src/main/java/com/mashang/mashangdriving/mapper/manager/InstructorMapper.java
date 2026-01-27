package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.DrivingInstructor;
import com.mashang.mashangdriving.domain.vo.student.AllInstructorListVo;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface InstructorMapper extends BaseMapper<DrivingInstructor> {

    AllInstructorListVo myInstructor(Long instructorId);

    List<AllInstructorListVo> allnstructorList();

    /**
     * 查询时间段内可预约教练
     */
    List<DrivingInstructor> selectAvailableInstructor(
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime
    );
}
