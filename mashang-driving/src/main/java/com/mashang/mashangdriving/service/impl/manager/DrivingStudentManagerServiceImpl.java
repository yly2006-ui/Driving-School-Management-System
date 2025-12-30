package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.Exception.BusinessException;
import com.mashang.mashangdriving.domain.entity.*;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingStudentCreate;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingStudentQuery;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo;
import com.mashang.mashangdriving.mapper.manager.*;
import com.mashang.mashangdriving.service.manager.IDrivingStudentManagerService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
public class DrivingStudentManagerServiceImpl extends ServiceImpl<DrivingStudentManagerMapper, DrivingStudent> implements IDrivingStudentManagerService {

    @Autowired
    private InstructorStudentManagerMapper instructorStudentManagerMapper;

    @Override
    public Page<DrivingStudentListVo> getList(DrivingStudent drivingStudent, Page<DrivingStudentListVo> page) {
        return baseMapper.getList(drivingStudent,page);
    }

    @Override
    public DrivingStudentListVo selectOne(DrivingStudentQuery query) throws BusinessException {
        if (query == null ||
                (query.getStudentId() == null &&
                        StringUtils.isBlank(query.getStudentName()) &&
                        StringUtils.isBlank(query.getPhone()) &&
                        StringUtils.isBlank(query.getIdNumber()))) {
            throw new BusinessException("请提供查询条件");
        }

        return baseMapper.selectOne(query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DrivingStudentListVo insertStudent(DrivingStudentCreate dto) {

        DrivingStudent studentEntity = new DrivingStudent();
        studentEntity.setStudentName(dto.getStudentName());
        studentEntity.setIdNumber(dto.getIdNumber());
        studentEntity.setPhone(dto.getPhone());
        studentEntity.setEmergencyPhone(dto.getEmergencyPhone());
        studentEntity.setDriverLicenseId(dto.getDriverLicenseId());
        studentEntity.setStatus("0");
        studentEntity.setDelFlag("0");
        if (dto.getStudentId() != null) {
            studentEntity.setUserId(100000L + dto.getStudentId());
        } else {
            studentEntity.setUserId(null);
        }
        studentEntity.setCreateTime(new Date());
        studentEntity.setUpdateTime(new Date());

        int i = baseMapper.insert(studentEntity);
        if (i<=0){
            throw new RuntimeException("保存失败");
        }
        DrivingInstructorStudent drivingInstructorStudent = new DrivingInstructorStudent();
        drivingInstructorStudent.setStudentId(studentEntity.getStudentId());
        drivingInstructorStudent.setSubjectId(dto.getDrivingInstructorStudent().getSubjectId());
        drivingInstructorStudent.setInstructorId(dto.getDrivingInstructorStudent().getInstructorId());
        drivingInstructorStudent.setDelFlag("0");

        int i1 = instructorStudentManagerMapper.insert(drivingInstructorStudent);
        if (i1 <= 0) {
            throw new RuntimeException("保存教练学员关系失败");
        }

        studentEntity.setDrivingInstructorStudent(drivingInstructorStudent);
        return getDrivingStudentListVo(studentEntity);
    }

    private static DrivingStudentListVo getDrivingStudentListVo(DrivingStudent studentEntity) {
        DrivingStudentListVo vo = new DrivingStudentListVo();
        vo.setStudentId(studentEntity.getStudentId());
        vo.setStudentName(studentEntity.getStudentName());
        vo.setPhone(studentEntity.getPhone());
        vo.setIdNumber(studentEntity.getIdNumber());
        vo.setStatus(studentEntity.getStatus());
        vo.setCreateTime(studentEntity.getCreateTime());
        vo.setUpdateTime(studentEntity.getUpdateTime());
        vo.setUserId(studentEntity.getUserId());
        vo.setDrivingInstructorStudent(studentEntity.getDrivingInstructorStudent());
        return vo;
    }
}
