package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingCourse;
import com.mashang.mashangdriving.domain.entity.DrivingLearningMaterials;
import com.mashang.mashangdriving.domain.vo.manager.DrivingCourseListVo;
import org.apache.ibatis.annotations.Param;

public interface DrivingLearningMaterialsMapper extends BaseMapper<DrivingLearningMaterials> {

}
