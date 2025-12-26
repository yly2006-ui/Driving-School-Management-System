package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.DrivingNotice;
import com.mashang.mashangdriving.domain.vo.student.StudentDataOverviewNoticeDtlVo;

import java.util.List;

public interface NoticeMapper extends BaseMapper<DrivingNotice> {

    List<StudentDataOverviewNoticeDtlVo> allDataOverviewNotice(Long userId);
}
