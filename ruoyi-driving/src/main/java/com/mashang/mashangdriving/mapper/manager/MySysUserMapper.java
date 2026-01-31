package com.mashang.mashangdriving.mapper.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashang.mashangdriving.domain.entity.MySysUser;
import com.mashang.mashangdriving.domain.vo.manager.ProfileInfoVO;

public interface MySysUserMapper extends BaseMapper<MySysUser> {

    //查询管理员信息
    ProfileInfoVO selectByuserId(Long userId);
}
