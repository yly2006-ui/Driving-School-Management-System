package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.MySysUser;
import com.mashang.mashangdriving.domain.vo.manager.ProfileInfoVO;
import com.mashang.mashangdriving.mapper.manager.MySysUserMapper;

import com.mashang.mashangdriving.service.manager.IMySysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MySysUserServiceImpl extends ServiceImpl<MySysUserMapper, MySysUser> implements IMySysUserService {
    @Autowired
    private MySysUserMapper mapper;
    @Override
    public ProfileInfoVO selectByuserId(Long userId) {
        return mapper.selectByuserId(userId);
    }
}
