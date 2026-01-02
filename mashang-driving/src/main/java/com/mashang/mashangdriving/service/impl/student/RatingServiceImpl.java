package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingRating;
import com.mashang.mashangdriving.mapper.student.RatingMapper;
import com.mashang.mashangdriving.service.student.IRatingService;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl extends ServiceImpl<RatingMapper, DrivingRating> implements IRatingService {
}
