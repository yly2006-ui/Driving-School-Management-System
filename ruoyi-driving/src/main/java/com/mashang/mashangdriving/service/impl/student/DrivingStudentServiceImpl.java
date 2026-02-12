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


    @Override
    public DrivingStudentDtlVo selectById(Long studentId) {
        return drivingStudentMapper.selectById(studentId);
    }

    @Override
    public String selectMail(Long userId) {
        return drivingStudentMapper.selectMail(userId);
    }


    // 固定你的服务器域名（和你之前说的完全一致）
    private static final String SERVER_DOMAIN = "http://mashang.eicp.vip:5555/ms_stu_pro339";

    public String updateAvatar(Long studentId, MultipartFile file) throws IOException {
        // 1. 上传文件，获取本地相对路径（比如 /profile/upload/2026/02/13/abc.png）
        String localPath = FileUploadUtils.upload(FileUploadUtils.getDefaultBaseDir(), file);

        // 2. 拼接你的服务器域名，生成可访问的完整URL（绝对匹配你的地址）
        String fullAvatarUrl = SERVER_DOMAIN + localPath;
        fullAvatarUrl = fullAvatarUrl.replaceAll("//", "/"); // 防止多斜杠（比如域名结尾带/的情况）

        // 3. 更新数据库（存的是完整可访问URL，不是本地路径）
        drivingStudentMapper.updateAvatar(studentId, fullAvatarUrl);

        return fullAvatarUrl;
    }

}