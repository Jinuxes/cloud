package com.jinuxes.cloud.service.api;

import com.github.pagehelper.PageInfo;
import com.jinuxes.cloud.entity.Role;

import java.util.List;

public interface RoleService {

    // 测试用
    void save(Role role);

    PageInfo<Role> getRolePageInfo(Integer pageNum, Integer pageSize, String keyword);


    void saveRole(Role role);

    Role getRoleByName(String name);

    void deleteRolesById(List<Integer> roleIdList);

    void updateRole(Role role);

    List<Role> getAssignedRole(Integer userId);

    List<Role> getUnAssignedRole(Integer userId);

    void saveUserAssignRole(Integer userId, List<Integer> roleIds);
}
