package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingRating;
import com.mashang.mashangdriving.domain.vo.manager.DrivingRatingStudentVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface DrivingRatingMapper extends BaseMapper<DrivingRating> {
    Page<DrivingRatingStudentVO> getRatingByInstructorWithStudentInfo(
            @Param("instructorId") Long instructorId,
            Page<DrivingRatingStudentVO> page,@Param("timeFilter") String timeFilter,@Param("scoreLevel")String scoreLevel);
}
