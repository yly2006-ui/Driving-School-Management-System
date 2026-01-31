package com.mashang.mashangdriving.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashang.mashangdriving.domain.entity.MySysUser;
import com.mashang.mashangdriving.domain.vo.manager.ProfileInfoVO;

public interface IMySysUserService extends IService<MySysUser> {
    //查询管理员信息
    ProfileInfoVO selectByuserId(Long userId);
}
