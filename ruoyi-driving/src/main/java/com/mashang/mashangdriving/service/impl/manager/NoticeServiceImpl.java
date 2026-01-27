package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingNotice;
import com.mashang.mashangdriving.domain.vo.student.StudentDataOverviewNoticeDtlVo;
import com.mashang.mashangdriving.mapper.manager.NoticeMapper;
import com.mashang.mashangdriving.service.manager.INoticeService;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, DrivingNotice> implements INoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<StudentDataOverviewNoticeDtlVo> allDataOverviewNotice() {

        Long userId = SecurityUtils.getLoginUser().getUserId();
        List<StudentDataOverviewNoticeDtlVo> studentDataOverviewNoticeDtlVos =
                noticeMapper.allDataOverviewNotice(userId);
        return studentDataOverviewNoticeDtlVos;
    }
}
