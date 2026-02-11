package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.DrivingSection;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingSectionCreate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingSectionUpdate;
import com.mashang.mashangdriving.mapper.manager.DrivingSectionMapper;
import com.mashang.mashangdriving.mapping.manager.DrivingSectionMapping;
import com.mashang.mashangdriving.service.manager.IDrivingSectionService;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DrivingSectionServiceImpl extends ServiceImpl<DrivingSectionMapper, DrivingSection> implements IDrivingSectionService {

    @Autowired
    private DrivingSectionMapper drivingSectionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSection(DrivingSectionUpdate drivingSectionUpdate) {
        // 查同章节下是否存在同名其他小节
        LambdaQueryWrapper<DrivingSection> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotNull(drivingSectionUpdate.getChapterId()),
                DrivingSection::getChapterId, drivingSectionUpdate.getChapterId());
        lqw.eq(StringUtils.isNotEmpty(drivingSectionUpdate.getSectionName()),
                DrivingSection::getSectionName, drivingSectionUpdate.getSectionName());
        lqw.ne(StringUtils.isNotNull(drivingSectionUpdate.getSectionId()),
                DrivingSection::getSectionId, drivingSectionUpdate.getSectionId());

        DrivingSection drivingSection = drivingSectionMapper.selectOne(lqw);
        if (drivingSection != null) {
            throw new RuntimeException("该章已存在此小节");
        }

        // 查询原始小节信息
        DrivingSection drivingSectioned = drivingSectionMapper.selectById(drivingSectionUpdate.getSectionId());
        if (drivingSectioned == null) {
            throw new RuntimeException("该章不存在此小节");
        }

        DrivingSection update = DrivingSectionMapping.INSTANCE.toUpdate(drivingSectionUpdate);
//        update.setSectionId(drivingSectionUpdate.getSectionId());



        return drivingSectionMapper.updateById(update);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertSection(DrivingSectionCreate drivingSectionCreate) {
        DrivingSection create = DrivingSectionMapping.INSTANCE.toCreate(drivingSectionCreate);
        LambdaQueryWrapper<DrivingSection>lqw=new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotEmpty(drivingSectionCreate.getSectionName()),
                DrivingSection::getSectionName,drivingSectionCreate.getSectionName());
        lqw.eq(StringUtils.isNotNull(drivingSectionCreate.getChapterId()),DrivingSection::getChapterId,
                drivingSectionCreate.getChapterId());
        DrivingSection drivingSection = drivingSectionMapper.selectOne(lqw);
        if (drivingSection!=null){
            throw new RuntimeException("此章下已有此小节名称");
        }


        return drivingSectionMapper.insert(create);
    }
}