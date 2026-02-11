package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.*;
import com.mashang.mashangdriving.domain.param.student.create.DrivingContentCreate;
import com.mashang.mashangdriving.mapper.student.DrivingAttributeUseridMapper;
import com.mashang.mashangdriving.mapper.student.DrivingContentMapper;
import com.mashang.mashangdriving.mapper.student.DrivingStudentMapper;
import com.mashang.mashangdriving.mapping.student.DrivingContentMapping;
import com.mashang.mashangdriving.service.student.IDrivingContentService;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrivingContentServiceImpl extends ServiceImpl<DrivingContentMapper, DrivingContent> implements IDrivingContentService {
    @Autowired
    private DrivingContentMapper drivingContentMapper;
    @Autowired
    private DrivingAttributeUseridMapper drivingAttributeUseridMapper;
    @Autowired
    private DrivingStudentMapper drivingStudentMapper;
    @Override
    public int insertContent(DrivingContentCreate create) {
        LambdaQueryWrapper<DrivingContent> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DrivingContent::getContectName,create.getContectName());
        DrivingContent one = drivingContentMapper.selectOne(lambdaQueryWrapper);
        if (one!=null) {
            throw new RuntimeException("已存在此名称学习小节");
        }
        DrivingContent created = DrivingContentMapping.INSTANCE.toCreate(create);
        int insert = drivingContentMapper.insert(created);
        if (insert<0){
            throw new RuntimeException("新增小节失败");
        }
        Long attributeId = drivingAttributeUseridMapper.selectAttributeIdBylableId(created.getLableId(),
                SecurityUtils.getUserId());
        LambdaQueryWrapper<DrivingAttributeUserid>attributeUserid=new LambdaQueryWrapper<>();
        attributeUserid.eq(DrivingAttributeUserid::getAttributeId,attributeId);
        attributeUserid.eq(DrivingAttributeUserid::getUserId,SecurityUtils.getUserId());
        int deleteById = drivingAttributeUseridMapper.delete(attributeUserid);
        if (!(deleteById >0)){
            throw new RuntimeException("课程完结表删除记录失败");
        }
        LambdaQueryWrapper<DrivingStudent>studentLambdaQueryWrapper=new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(DrivingStudent::getUserId,SecurityUtils.getUserId());
        DrivingStudent drivingStudent = drivingStudentMapper.selectOne(studentLambdaQueryWrapper);
        drivingStudent.setStatus("0");

        return drivingStudentMapper.updateById(drivingStudent);
    }
}
