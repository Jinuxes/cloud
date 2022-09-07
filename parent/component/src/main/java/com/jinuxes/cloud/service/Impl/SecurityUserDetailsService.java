package com.jinuxes.cloud.service.Impl;

import com.jinuxes.cloud.entity.Role;
import com.jinuxes.cloud.entity.SecurityUserDetail;
import com.jinuxes.cloud.entity.User;
import com.jinuxes.cloud.exception.LoginAccountErrorException;
import com.jinuxes.cloud.service.api.AuthorityService;
import com.jinuxes.cloud.service.api.RoleService;
import com.jinuxes.cloud.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Security的UserDetailService实现类
 */
@Component
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    AuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {

        // 根据账号名查询User对象
        User user = userService.getUserByAccount(account);

        // 判断该账号是否存在，不存在抛出异常
        // 注意：AuthenticationException异常是有SpringSecurity的异常处理器进行处理的，springMVC的全局异常处理器捕获不到这些异常
        if(user == null){

            /*
             * 方式一：直接抛出UsernameNotFoundException异常
             * 但是这里如果抛出UsernameNotFoundException异常，错误信息不会显示“账号不存在”，
             * 依然是“Bad credentials”。原因是因为因为 AbstractUserDetailsAuthenticationProvider 类的 authenticate() 方法中
             * hideUserNotFoundExceptions = true 的原因
             */
            // throw new UsernameNotFoundException("账号不存在");

            /*
             * 方式二：自定义一个异常类，继承AuthenticationException，这样抛出这个自定义的异常类并加上msg，就可以在登录异常是返回对应的信息在页面上
             */
            throw new LoginAccountErrorException("账号错误");

            /*
             * 方式三：直接抛出BadCredentialsException异常类，也是可以显示错误信息在页面上的
             */
            // throw new BadCredentialsException("账号不存在");
        }

        // 获取userId
        Integer userId = user.getId();

        // 根据userId查询角色信息
        List<Role> assignedRoleList = roleService.getAssignedRole(userId);

        // 根据userId查询权限信息
        List<String> authorityNameList = authorityService.getAssignedAuthorityNameByUserId(userId);

        // 创建集合对象用来存储GrantedAuthority(授权信息)
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 遍历assignedRoleList存入角色信息
        for(Role role:assignedRoleList){
            // 一定要注意：角色不要漏了加前缀！！！，权限则不需要前缀，直接权限名即可
            String roleName = "ROLE_" + role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }

        // 遍历authorities存入权限信息
        for(String authorityName:authorityNameList){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authorityName);
            authorities.add(simpleGrantedAuthority);
        }

        // 封装SecurityUserDetail对象(UserDetails对象)
        SecurityUserDetail securityUserDetail = new SecurityUserDetail(user,authorities);

        return securityUserDetail;
    }
}
