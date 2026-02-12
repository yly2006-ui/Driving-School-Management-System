package com.mashang.mashangdriving.mapper.student;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.DrivingContent;
import com.mashang.mashangdriving.domain.vo.student.DrivingCourseAndContentVo;
import org.apache.ibatis.annotations.Param;

public interface DrivingContentMapper extends BaseMapper<DrivingContent> {
    // 新增：查询详情+上/下节ID
    DrivingCourseAndContentVo selectContentWithPrevNext(@Param("contentId") Long contentId);
}
