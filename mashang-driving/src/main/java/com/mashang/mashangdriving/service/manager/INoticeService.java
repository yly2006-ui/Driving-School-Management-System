package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingNotice;
import com.mashang.mashangdriving.domain.vo.manager.DataOverviewNoticeDtlVo;
import com.mashang.mashangdriving.domain.vo.student.StudentDataOverviewNoticeDtlVo;

import java.util.List;

public interface INoticeService extends IService<DrivingNotice> {

    List<StudentDataOverviewNoticeDtlVo> allDataOverviewNotice();
}
