package com.mashang.mashangdriving.mapper.student;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.DrivingAttributeUserid;
import org.apache.ibatis.annotations.Param;

public interface DrivingAttributeUseridMapper extends BaseMapper<DrivingAttributeUserid> {


    //根据内容id查询课程id
    Long selectAttributeId(@Param("contentId") Long  contentId,@Param("userId") Long userId);

    //查询标签id查询课程id
    Long selectAttributeIdBylableId(@Param("lableId") Long  lableId,@Param("userId") Long userId);

    //查询完成课程数
    Long selectFinishedCourse(Long userId);

    //查询课程数量
    Long selectCourseTotal();
}
