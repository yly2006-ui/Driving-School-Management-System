package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.Exception.BusinessException;
import com.mashang.mashangdriving.domain.entity.DrivingStudent;
import com.mashang.mashangdriving.domain.param.manager.query.DrivingStudentQuery;
import com.mashang.mashangdriving.domain.vo.manager.DrivingStudentListVo;
import com.mashang.mashangdriving.mapper.manager.DrivingStudentManagerMapper;
import com.mashang.mashangdriving.service.manager.IDrivingStudentManagerService;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DrivingStudentManagerServiceImpl extends ServiceImpl<DrivingStudentManagerMapper, DrivingStudent> implements IDrivingStudentManagerService {


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
}
