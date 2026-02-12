package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.vo.student.DrivingStudentDtlVo;
import com.mashang.mashangdriving.mapper.student.DrivingStudentMapper;
import com.mashang.mashangdriving.service.student.IDrivingStudentService;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @Override
    public int uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        // 1. 用若依工具类上传图片，得到「本地相对路径」（比如 /profile/upload/2026/02/11/xxx.png）
        String localPath = FileUploadUtils.upload(RuoYiConfig.getUploadPath(), file);

        // 2. 核心：拼接服务器域名，生成完整URL（这一步你之前没做！）
        String serverDomain = RuoYiConfig.getDomain(); // 从配置读取：http://mashang.eicp.vip:5555/ms_stu_pro339
        String fullUrl = serverDomain + localPath;     // 拼接后：http://xxx:5555/ms_stu_pro339/profile/upload/xxx.png
        fullUrl = fullUrl.replaceAll("//", "/");       // 去除多余的/，避免格式错误

        // 3. 更新数据库：存入完整URL，而非本地路径
        DrivingStudent student = new DrivingStudent();
        student.setStudentId(studentId);
        student.setAvatar(fullUrl); // 存完整URL
        return drivingStudentMapper.updateById(student);
    }



}