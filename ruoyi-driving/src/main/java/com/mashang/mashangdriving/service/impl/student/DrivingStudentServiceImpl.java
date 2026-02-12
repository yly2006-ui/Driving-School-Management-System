package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.vo.student.DrivingStudentDtlVo;
import com.mashang.mashangdriving.mapper.student.DrivingStudentMapper;
import com.mashang.mashangdriving.service.student.IDrivingStudentService;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrivingStudentServiceImpl extends ServiceImpl<DrivingStudentMapper, DrivingStudent> implements IDrivingStudentService {
    @Autowired
    private DrivingStudentMapper drivingStudentMapper;

    // 1. 直接定义本地前缀和服务器前缀（核心修改）
    private static final String LOCAL_PREFIX = "http://127.0.0.1:11339";
    private static final String SERVER_PREFIX = "http://mashang.eicp.vip:5555/ms_stu_pro339";

    @Override
    public DrivingStudentDtlVo selectById(Long studentId) {
        // 2. 查询原始数据
        DrivingStudentDtlVo studentVo = drivingStudentMapper.selectById(studentId);

        // 3. 处理avatar前缀（核心逻辑）
        if (studentVo != null && StringUtils.isNotBlank(studentVo.getAvatar())) {
            String avatar = studentVo.getAvatar();
            // 去掉本地前缀
            if (avatar.startsWith(LOCAL_PREFIX)) {
                avatar = avatar.replace(LOCAL_PREFIX, "");
            }
            // 纯路径拼接服务器前缀，外网地址不处理
            if (!avatar.startsWith("http")) {
                avatar = SERVER_PREFIX + avatar;
            }
            studentVo.setAvatar(avatar);
        }

        return studentVo;
    }

    @Override
    public String selectMail(Long userId) {
        return drivingStudentMapper.selectMail(userId);
    }
}