package com.mashang.mashangdriving.service.impl.student;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.vo.student.DrivingStudentDtlVo;
import com.mashang.mashangdriving.mapper.student.DrivingStudentMapper;
import com.mashang.mashangdriving.service.student.IDrivingStudentService;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DrivingStudentServiceImpl extends ServiceImpl<DrivingStudentMapper, DrivingStudent> implements IDrivingStudentService {
    @Autowired
    private DrivingStudentMapper drivingStudentMapper;

    @Autowired
    private RuoYiConfig ruoYiConfig;

    // 纯原生查询：只拼接域名，不修改路径格式
    @Override
    public DrivingStudentDtlVo selectById(Long studentId) {
        DrivingStudentDtlVo vo = drivingStudentMapper.selectById(studentId);
        if (vo != null && vo.getAvatar() != null && !vo.getAvatar().startsWith("http")) {
            // 只做2件事：1. 拼接域名 2. 处理多余斜杠（仅容错，不修改路径本身）
            String fullUrl = ruoYiConfig.getDomain() + vo.getAvatar();
            fullUrl = fullUrl.replaceAll("(?<!http:|https:)//+", "/");
            vo.setAvatar(fullUrl);
        }
        return vo;
    }

    @Override
    public String selectMail(Long userId) {
        return drivingStudentMapper.selectMail(userId);
    }

    // 纯原生上传：只存若依返回的原始路径
    @Override
    public String updateAvatar(Long studentId, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("头像文件不能为空");
        }
        // 1. 若依原生上传，返回：/profile/upload/20260212/xxx.png（默认路径，不用改）
        String ruoyiOriginalPath = FileUploadUtils.upload(FileUploadUtils.getDefaultBaseDir(), file);

        // 2. 数据库只存这个原始路径，不做任何修改
        drivingStudentMapper.updateAvatar(studentId, ruoyiOriginalPath);

        // 3. 拼接域名，返回完整URL
        String fullAvatarUrl = ruoYiConfig.getDomain() + ruoyiOriginalPath;
        fullAvatarUrl = fullAvatarUrl.replaceAll("(?<!http:|https:)//+", "/");
        return fullAvatarUrl;
    }
}