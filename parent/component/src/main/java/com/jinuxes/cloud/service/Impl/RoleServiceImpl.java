package com.jinuxes.cloud.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinuxes.cloud.entity.Role;
import com.jinuxes.cloud.exception.AccountAlreadyUseException;
import com.jinuxes.cloud.exception.RoleAlreadyExistException;
import com.jinuxes.cloud.mapper.RoleMapper;
import com.jinuxes.cloud.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    // 测试用
    @Override
    public void save(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public PageInfo<Role> getRolePageInfo(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum,pageSize);
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        return pageInfo;
    }

    @Override
    public void saveRole(Role role) {
        Role result = getRoleByName(role.getName());
        if(result == null){
            roleMapper.insert(role);
        }else{
            throw new RoleAlreadyExistException("该角色已经存在");
        }
    }

    @Override
    public Role getRoleByName(String name) {
        return roleMapper.selectRoleByName(name);
    }

    @Override
    public void deleteRolesById(List<Integer> roleIdList) {
        roleMapper.deleteByRolesId(roleIdList);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByRoleId(role);
    }

    @Override
    public List<Role> getAssignedRole(Integer userId) {
        return roleMapper.selectAssignedRole(userId);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer userId) {
        return roleMapper.selectUnAssignRole(userId);
    }

    @Override
    public void saveUserAssignRole(Integer userId, List<Integer> roleIds) {
        // 为了简化操作：先根据userId删除中间表inner_user_role中旧的数据，再根据roleIds保存全部新的数据
        // 1.根据userId删除旧的关联关系数据
        roleMapper.deleteUserRoleRelationship(userId);

        // 2.如果roleIds不为空，就是有分配到角色，根据roleIds和userId保存新的关联关系
        if(roleIds != null && roleIds.size() > 0) {
            roleMapper.insertUserRoleRelationship(userId, roleIds);
        }
    }
}
