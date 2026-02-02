package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.Exception.BusinessException;
import com.mashang.mashangdriving.domain.entity.DrivingDriverLicenseType;
import com.mashang.mashangdriving.domain.entity.DrivingInstructorStudent;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingStudentCreate;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingStudentQuery;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingStudentManagerUpdate;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo1;
import com.mashang.mashangdriving.mapper.manager.DrivingDriverLicenseTypeMapper;
import com.mashang.mashangdriving.mapper.manager.DrivingStudentManagerMapper;
import com.mashang.mashangdriving.mapper.manager.InstructorStudentManagerMapper;
import com.mashang.mashangdriving.service.manager.IDrivingStudentManagerService;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DrivingStudentManagerServiceImpl extends ServiceImpl<DrivingStudentManagerMapper, DrivingStudent> implements IDrivingStudentManagerService {

    @Autowired
    private InstructorStudentManagerMapper instructorStudentManagerMapper;
    @Autowired
    private DrivingDriverLicenseTypeMapper drivingDriverLicenseTypeMapper;

    @Override
    public Page<DrivingStudentListVo1> getList( Page<DrivingStudentListVo1> page) {
        return baseMapper.getList(page);
    }

    @Override
    public Page<DrivingStudentListVo1> selectOne(DrivingStudentQuery query,Page<DrivingStudentListVo1> page)  {
        return baseMapper.selectOneStudent(query,page);
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
        studentEntity.setCreateTime(new Date());
        studentEntity.setUpdateTime(new Date());

        int i = baseMapper.insert(studentEntity);
        if (i<=0){
            throw new RuntimeException("保存失败");
        }

        if (studentEntity.getStudentId() != null) {
            Long userId = 100000L + studentEntity.getStudentId();
            studentEntity.setUserId(userId);
            baseMapper.updateById(studentEntity);
        }else {
            studentEntity.setUserId(null);
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

        LambdaQueryWrapper<DrivingDriverLicenseType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DrivingDriverLicenseType::getDriverLicenseId, dto.getDriverLicenseId());
        DrivingDriverLicenseType drivingDriverLicenseType = drivingDriverLicenseTypeMapper.selectOne(wrapper);
        if (drivingDriverLicenseType == null) {
            // 记录日志
            System.out.println("警告：驾照类型ID " + dto.getDriverLicenseId() + " 不存在");
            studentEntity.setDriverLicenseName("");
            studentEntity.setDriverLicenseCode("");
        } else {
            studentEntity.setDriverLicenseName(drivingDriverLicenseType.getDriverLicenseName());
            studentEntity.setDriverLicenseCode(drivingDriverLicenseType.getDriverLicenseCode());
        }
        studentEntity.setDrivingInstructorStudent(drivingInstructorStudent);
        return getDrivingStudentListVo(studentEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DrivingStudentListVo updateStudent(DrivingStudentManagerUpdate dto) {

        if(dto.getStudentId() == null){
            throw new RuntimeException("学生ID不能为空");
        }

        LambdaQueryWrapper<DrivingDriverLicenseType> wrapper = new LambdaQueryWrapper<>();
        LambdaUpdateWrapper<DrivingStudent> drivingStudentLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        drivingStudentLambdaUpdateWrapper.eq( DrivingStudent::getStudentId, dto.getStudentId());

        wrapper.eq(DrivingDriverLicenseType::getDriverLicenseId, dto.getDriverLicenseId());
        DrivingDriverLicenseType driverLicenseType = drivingDriverLicenseTypeMapper.selectOne(wrapper);
        if(driverLicenseType != null){
            drivingStudentLambdaUpdateWrapper.eq(DrivingStudent::getStudentId, dto.getStudentId())
                    .set(DrivingStudent::getDriverLicenseId, dto.getDriverLicenseId());
        }

        if (StringUtils.isNotBlank(dto.getStudentName())) {
            drivingStudentLambdaUpdateWrapper.set(DrivingStudent::getStudentName, dto.getStudentName());
        }
        if(StringUtils.isNotBlank(dto.getEmergencyPhone())){
            drivingStudentLambdaUpdateWrapper.set(DrivingStudent::getEmergencyPhone, dto.getEmergencyPhone());
        }

        if (StringUtils.isNotBlank(dto.getIdNumber())) {
            drivingStudentLambdaUpdateWrapper.set(DrivingStudent::getIdNumber, dto.getIdNumber());
        }
        if(dto.getEmergencyPhone() != null){
            drivingStudentLambdaUpdateWrapper.set(DrivingStudent::getEmergencyPhone, dto.getEmergencyPhone());
        }
        if (StringUtils.isNotBlank(dto.getIdNumberFront())){
            drivingStudentLambdaUpdateWrapper.set(DrivingStudent::getIdNumberFront, dto.getIdNumberFront());
        }
        if (StringUtils.isNotBlank(dto.getIdNumberBack())){
            drivingStudentLambdaUpdateWrapper.set(DrivingStudent::getIdNumberBack, dto.getIdNumberBack());
        }
        if (StringUtils.isNotBlank(dto.getStatus())){
            drivingStudentLambdaUpdateWrapper.set(DrivingStudent::getStatus, dto.getStatus());
        }

        drivingStudentLambdaUpdateWrapper.set( DrivingStudent::getUpdateTime, new Date());
        int i = baseMapper.update(null, drivingStudentLambdaUpdateWrapper);
        if (i <= 0) {
            throw new RuntimeException("学员信息修改失败");
        }
//教练学员表
        if (dto.getDrivingInstructorStudent()!=null){

            LambdaUpdateWrapper<DrivingInstructorStudent> drivingInstructorStudentLambdaUpdateWrapper = new LambdaUpdateWrapper<>();

            drivingInstructorStudentLambdaUpdateWrapper.eq(DrivingInstructorStudent::getStudentId, dto.getStudentId());

            boolean hasUpdate = false;
        if (dto.getDrivingInstructorStudent().getInstructorId() != null) {
            drivingInstructorStudentLambdaUpdateWrapper.set(DrivingInstructorStudent::getInstructorId,
                    dto.getDrivingInstructorStudent().getInstructorId());
            hasUpdate = true;
        }
        if(dto.getDrivingInstructorStudent().getSubjectId()!=null){
            drivingInstructorStudentLambdaUpdateWrapper.set(DrivingInstructorStudent::getSubjectId,
                    dto.getDrivingInstructorStudent().getSubjectId());
            hasUpdate = true;
        }
        if (hasUpdate) {
            int update = instructorStudentManagerMapper.update(null, drivingInstructorStudentLambdaUpdateWrapper);
            if (update<=0) {
                throw new RuntimeException("教练学员表更新失败");
            }
        }
        }

        DrivingStudent student = baseMapper.selectById(dto.getStudentId());
        if (student == null) {
            throw new RuntimeException("学员不存在");
        }
        LambdaQueryWrapper<DrivingInstructorStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DrivingInstructorStudent::getStudentId, dto.getStudentId());
        DrivingInstructorStudent drivingInstructorStudent = instructorStudentManagerMapper.selectOne(queryWrapper);
        student.setDrivingInstructorStudent(drivingInstructorStudent);
        if (driverLicenseType != null) {
            student.setDriverLicenseName(driverLicenseType.getDriverLicenseName());
        }
        if (driverLicenseType != null) {
            student.setDriverLicenseCode(driverLicenseType.getDriverLicenseCode());
        }

        return getDrivingStudentListVo(student);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long studentId) {

        if (studentId == null) {
            throw new RuntimeException("该学员不存在");
        }
        LambdaUpdateWrapper<DrivingStudent> studentLambdaUpdateWrapper = new LambdaUpdateWrapper<DrivingStudent>()
                .eq(DrivingStudent::getStudentId, studentId)
                .set(DrivingStudent::getUpdateTime, new Date())
                .set(DrivingStudent::getDelFlag, 2);
        int i = baseMapper.update(null, studentLambdaUpdateWrapper);
        if (i <= 0) {
            throw new RuntimeException("学员表删除失败");
        }
        LambdaUpdateWrapper<DrivingInstructorStudent> instructorStudentLambdaUpdateWrapper = new LambdaUpdateWrapper<DrivingInstructorStudent>()
                .eq(DrivingInstructorStudent::getStudentId, studentId)
                .set(DrivingInstructorStudent::getDelFlag,2);
        int update = instructorStudentManagerMapper.update(null, instructorStudentLambdaUpdateWrapper);


        return 1;
    }

    private static DrivingStudentListVo getDrivingStudentListVo(DrivingStudent studentEntity) {
        DrivingStudentListVo vo = new DrivingStudentListVo();
        vo.setStudentId(studentEntity.getStudentId());
        vo.setStudentName(studentEntity.getStudentName());
        vo.setDriverLicenseId(studentEntity.getDriverLicenseId());
        vo.setPhone(studentEntity.getPhone());
        vo.setIdNumber(studentEntity.getIdNumber());
        vo.setStatus(studentEntity.getStatus());
        vo.setCreateTime(studentEntity.getCreateTime());
        vo.setUpdateTime(studentEntity.getUpdateTime());
        vo.setUserId(studentEntity.getUserId());
        vo.setDrivingInstructorStudent(studentEntity.getDrivingInstructorStudent());
        vo.setDriverLicenseName(studentEntity.getDriverLicenseName());
        vo.setDriverLicenseCode(studentEntity.getDriverLicenseCode());
        vo.setEmergencyPhone(studentEntity.getEmergencyPhone());

        return vo;
    }
}
