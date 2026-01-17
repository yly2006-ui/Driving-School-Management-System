package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingAttributeUserid;
import com.mashang.mashangdriving.domain.entity.DrivingCourseRecord;
import com.mashang.mashangdriving.domain.vo.student.DrivingCourseAttributeVO;
import com.mashang.mashangdriving.mapper.student.DrivingAttributeUseridMapper;
import com.mashang.mashangdriving.mapper.student.DrivingCourseAttributeMapper;
import com.mashang.mashangdriving.mapper.student.DrivingCourseRecordMapper;
import com.mashang.mashangdriving.service.student.IDrivingCourseRecordService;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrivingCourseRecordServiceImpl extends ServiceImpl<DrivingCourseRecordMapper, DrivingCourseRecord> implements IDrivingCourseRecordService {
    @Autowired
    private DrivingCourseAttributeMapper drivingCourseAttributeMapper;
    @Autowired
    private DrivingAttributeUseridMapper drivingAttributeUseridMapper;
    @Override
    public void insertRecord(Long attributeId, Long userId) {

        List<DrivingCourseAttributeVO> drivingCourseAttributeVOS1 = drivingCourseAttributeMapper.countFinish(attributeId,
                userId);
        Long total = drivingCourseAttributeMapper.total(attributeId);
        System.out.println("查询出的课程下的总条数"+total);
        for (DrivingCourseAttributeVO drivingCourseAttributeVO : drivingCourseAttributeVOS1) {
            String finish = drivingCourseAttributeVO.getFinish();
            int i = Integer.parseInt(finish);
            int i1 = Integer.parseInt(String.valueOf(total));
            if (i ==i1){
                DrivingAttributeUserid drivingAttributeUserid=new DrivingAttributeUserid();
                drivingAttributeUserid.setUserId(SecurityUtils.getUserId());
                drivingAttributeUserid.setStatus("1");
                drivingAttributeUserid.setAttributeId(attributeId);
                int insert = drivingAttributeUseridMapper.insert(drivingAttributeUserid);
                if (insert<0){
                    throw new RuntimeException("未插入课程完结表");
                }
            }
        }
    }


}
