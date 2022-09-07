package com.jinuxes.cloud.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * Spring Security的用户实体类，用于进行认证和授权
 */
public class SecurityUserDetail extends org.springframework.security.core.userdetails.User{
    private static final long serialVersionUID = 6854824L;

    // 封装原始对象
    private com.jinuxes.cloud.entity.User originalUser;

    public SecurityUserDetail(com.jinuxes.cloud.entity.User originalUser, List<GrantedAuthority> authorities){
        // 调用父类构造器
        super(originalUser.getAccount(),originalUser.getPassword(),authorities);
        // 保存原始对象到本类属性
        this.originalUser = originalUser;
        // 擦除原始对象中的密码
        this.originalUser.setPassword(null);
    }

    public com.jinuxes.cloud.entity.User getOriginalUser(){
        return this.originalUser;
    }
}
