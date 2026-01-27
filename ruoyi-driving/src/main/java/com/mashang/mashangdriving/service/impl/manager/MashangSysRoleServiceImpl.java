package com.mashang.mashangdriving.service.impl.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashang.mashangdriving.domain.entity.MashangSysRole;
import com.mashang.mashangdriving.mapper.manager.MashangSysRoleMapper;
import com.mashang.mashangdriving.service.manager.IMashangSysRoleService;
import org.springframework.stereotype.Service;

// 1. 指定唯一的 Bean 名称，解决和原有 SysRoleService 的冲突
@Service("mashangSysRoleService")
// 2. 继承 ServiceImpl，泛型对应 Mapper 和实体类
public class MashangSysRoleServiceImpl extends ServiceImpl<MashangSysRoleMapper, MashangSysRole>
        implements IMashangSysRoleService {

}