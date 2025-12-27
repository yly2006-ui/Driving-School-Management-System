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

import java.util.List;

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
        update.setSectionId(drivingSectionUpdate.getSectionId());


        Integer newSort = update.getSectionSort();
        Integer oldSort = drivingSectioned.getSectionSort();

        // 仅当新/旧排序都不为空且不相等时，才执行批量调整（避免空指针和无意义更新）
        if (newSort != null && oldSort != null && !newSort.equals(oldSort)) {
            LambdaUpdateWrapper<DrivingSection> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            // 条件1：仅修改同章节的小节（关键，防止全表数据混乱）
            lambdaUpdateWrapper.eq(DrivingSection::getChapterId, drivingSectionUpdate.getChapterId());
            // 条件2：排除当前修改的小节（避免自身排序被二次修改）
            lambdaUpdateWrapper.ne(DrivingSection::getSectionId, drivingSectionUpdate.getSectionId());

            if (newSort > oldSort) {
                // 场景1：新排序 > 旧排序（小节后移），仅调整 [oldSort < sectionSort ≤ newSort] 的小节
                lambdaUpdateWrapper.gt(DrivingSection::getSectionSort, oldSort);
                lambdaUpdateWrapper.le(DrivingSection::getSectionSort, newSort);
                lambdaUpdateWrapper.setSql("section_sort = section_sort - 1");
            } else {
                // 场景2：新排序 < 旧排序（小节前移），仅调整 [newSort ≤ sectionSort < oldSort] 的小节
                lambdaUpdateWrapper.ge(DrivingSection::getSectionSort, newSort);
                lambdaUpdateWrapper.lt(DrivingSection::getSectionSort, oldSort);
                lambdaUpdateWrapper.setSql("section_sort = section_sort + 1");
            }

            // 执行批量更新（仅修改符合条件的记录，不影响无关数据）
            drivingSectionMapper.update(null, lambdaUpdateWrapper);
        }
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

        // 2. 获取当前章节最大排序值
        LambdaQueryWrapper<DrivingSection> maxSortQuery = new LambdaQueryWrapper<>();
        maxSortQuery.eq(DrivingSection::getChapterId, drivingSectionCreate.getChapterId());
        maxSortQuery.orderByDesc(DrivingSection::getSectionSort).last("LIMIT 1");
        DrivingSection result = drivingSectionMapper.selectOne(maxSortQuery);
        Integer maxSort = result.getSectionSort();
        // 3. 如果传入的排序大于当前最大排序，则设置为maxSort+1
        if (drivingSectionCreate.getSectionSort() > maxSort) {
            create.setSectionSort(maxSort + 1);
        } else {
            // 4. 否则，插入到指定位置，后面的排序全部+1
            LambdaUpdateWrapper<DrivingSection> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(DrivingSection::getChapterId, drivingSectionCreate.getChapterId());
            updateWrapper.ge(DrivingSection::getSectionSort, drivingSectionCreate.getSectionSort());
            updateWrapper.setSql("section_sort = section_sort + 1");
            int update = drivingSectionMapper.update(null, updateWrapper);
            System.out.println("调整了" + update + "个小节的排序");
        }
        return drivingSectionMapper.insert(create);
    }
}