package com.mashang.mashangdriving.domain.vo.manager;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * 个人中心用户信息响应类
 */
@Data
public class ProfileInfoVO {
    // 基础用户信息
    private Long userId;
    private String userName;
    private String nickName;
    private String phoneNumber;
    private String email;

    // 登录信息
    private String avatar;
    private String roleName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginDate;
}