package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.DrivingSection;
import com.mashang.mashangdriving.domain.param.manager.create.DrivingSectionCreate;
import com.mashang.mashangdriving.domain.param.manager.update.DrivingSectionUpdate;

public interface IDrivingSectionService extends IService<DrivingSection> {

    //修改小节
    int updateSection(DrivingSectionUpdate drivingSectionUpdate);

    //新增小节
    int insertSection (DrivingSectionCreate drivingSectionCreate);
}
