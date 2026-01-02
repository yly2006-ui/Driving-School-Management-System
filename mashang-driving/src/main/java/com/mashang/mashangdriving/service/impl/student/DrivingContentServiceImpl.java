package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingContent;
import com.mashang.mashangdriving.mapper.student.DrivingContentMapper;
import com.mashang.mashangdriving.service.student.IDrivingContentService;
import org.springframework.stereotype.Service;

@Service
public class DrivingContentServiceImpl extends ServiceImpl<DrivingContentMapper, DrivingContent> implements IDrivingContentService {
}
