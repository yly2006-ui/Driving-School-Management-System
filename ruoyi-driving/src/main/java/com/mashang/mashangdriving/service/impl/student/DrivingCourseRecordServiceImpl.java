package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingAttributeUserid;
import com.mashang.mashangdriving.domain.entity.DrivingContent;
import com.mashang.mashangdriving.domain.entity.DrivingCourseRecord;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.param.student.update.DrivingCourseRecordQuery;
import com.mashang.mashangdriving.domain.vo.student.DrivingCourseAttributeVO;
import com.mashang.mashangdriving.domain.vo.student.DrivingStudentDtlVo;
import com.mashang.mashangdriving.mapper.student.*;
import com.mashang.mashangdriving.service.student.IDrivingCourseRecordService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DrivingCourseRecordServiceImpl extends ServiceImpl<DrivingCourseRecordMapper, DrivingCourseRecord> implements IDrivingCourseRecordService {
    @Autowired
    private DrivingCourseAttributeMapper drivingCourseAttributeMapper;
    @Autowired
    private DrivingAttributeUseridMapper drivingAttributeUseridMapper;
    @Autowired
    private DrivingCourseRecordMapper drivingCourseRecordMapper;
    @Autowired
    private DrivingContentMapper drivingContentMapper;
    @Autowired
    private DrivingStudentMapper drivingStudentMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public int updateRecord(DrivingCourseRecordQuery drivingCourseRecordQuery) {
        //判断是否存在数据
        LambdaQueryWrapper<DrivingCourseRecord> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DrivingCourseRecord::getUserId, SecurityUtils.getUserId());
        lambdaQueryWrapper.eq(DrivingCourseRecord::getContentId,drivingCourseRecordQuery.getContentId());
        DrivingCourseRecord drivingCourseRecord = drivingCourseRecordMapper.selectOne(lambdaQueryWrapper);
        if (drivingCourseRecord==null){throw new RuntimeException("不存在此数据如未学习请先学习");}
        else{
            drivingCourseRecord.setFinishedHours(drivingCourseRecordQuery.getViewTime());
            //更新观看时间
            int i = drivingCourseRecordMapper.updateById(drivingCourseRecord);
            if (!(i >0)){
                throw new RuntimeException("观看时间更新失败");
            }
        }

        //查询小节
        LambdaQueryWrapper<DrivingContent>drivingContentLambdaQueryWrapper=new LambdaQueryWrapper<>();
        drivingContentLambdaQueryWrapper.eq(DrivingContent::getContentId,drivingCourseRecordQuery.getContentId());
        DrivingContent content = drivingContentMapper.selectOne(drivingContentLambdaQueryWrapper);

        //获取小节时间与观看时间
        String contentTime = content.getContentTime();
        String finishedHours = drivingCourseRecord.getFinishedHours();

        //强转
        int i = Integer.parseInt(finishedHours);
        int i1 = Integer.parseInt(contentTime);

        if (i>=i1){
            drivingCourseRecord.setStatus("0");
            int b = drivingCourseRecordMapper.updateById(drivingCourseRecord);
            if (!(b >0)){
                throw new RuntimeException("课程学习记录状态为已完成更新失败");
            }
        }else {
            drivingCourseRecord.setStatus("1");
            int b = drivingCourseRecordMapper.updateById(drivingCourseRecord);
            if (!(b >0)){
                throw new RuntimeException("课程学习记录状态为学习中更新失败");
        }
    }
        Long attributeId = drivingAttributeUseridMapper.selectAttributeId(drivingCourseRecordQuery.getContentId(),
                SecurityUtils.getUserId());
        List<DrivingCourseAttributeVO> drivingCourseAttributeVOS1 = drivingCourseAttributeMapper.countFinish(attributeId,
                SecurityUtils.getUserId());
        Long total = drivingCourseAttributeMapper.total(attributeId);
        System.out.println("查询出的课程下的总条数"+total);
        for (DrivingCourseAttributeVO drivingCourseAttributeVO : drivingCourseAttributeVOS1) {
            String finish = drivingCourseAttributeVO.getFinish();
            int finished = Integer.parseInt(finish);
            int totaled = Integer.parseInt(String.valueOf(total));
            if (finished ==totaled){
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

        //判断课程数与学员学习的课程数
        Long courseTotal = drivingAttributeUseridMapper.selectCourseTotal();
        Long finishedCourse = drivingAttributeUseridMapper.selectFinishedCourse(SecurityUtils.getUserId());
        if (courseTotal.equals( finishedCourse)){
            LambdaQueryWrapper<DrivingStudent>studentLambdaQueryWrapper=new LambdaQueryWrapper<>();
            studentLambdaQueryWrapper.eq(DrivingStudent::getUserId,SecurityUtils.getUserId());
            DrivingStudent drivingStudent = drivingStudentMapper.selectOne(studentLambdaQueryWrapper);
            drivingStudent.setStatus("1");
            int updateById = drivingStudentMapper.updateById(drivingStudent);
            if (!(updateById >0)){
                throw new RuntimeException("设置学员学习状态失败");
            }
        }
        return i;
    }


    }
