package com.jinuxes.cloud.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinuxes.cloud.entity.Authority;
import com.jinuxes.cloud.exception.AuthorityNameAlreadyExistException;
import com.jinuxes.cloud.mapper.AuthorityMapper;
import com.jinuxes.cloud.service.api.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityMapper authorityMapper;

    // 测试用
    @Override
    public void save(Authority authority) {
        authorityMapper.insert(authority);
    }

    @Override
    public PageInfo<Authority> getAuthorityPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum,pageSize);
        List<Authority> authorityList = authorityMapper.selectAuthorityByKeyword(keyword);
        PageInfo<Authority> pageInfo = new PageInfo<>(authorityList);
        return pageInfo;
    }

    @Override
    public void saveAuthority(Authority authority) {
        Authority result = getAuthorityByAuthorityName(authority.getName());
        if(result == null){
            authorityMapper.insert(authority);
        }else{
            throw new AuthorityNameAlreadyExistException("账号已被使用");
        }
    }

    public Authority getAuthorityByAuthorityName(String name) {
        return authorityMapper.selectAuthorityByName(name);
    }

    @Override
    public void deleteAuthoritysById(List<Integer> authorityIdList) {
        authorityMapper.deleteAuthoritysById(authorityIdList);
    }

    @Override
    public void updateAuthority(Authority authority) {
        authorityMapper.updateAuthorityById(authority);
    }

    @Override
    public List<Authority> getAssignedAuthority(Integer roleId) {
        return authorityMapper.selectAssignedAuthority(roleId);
    }

    @Override
    public List<Authority> getUnAssignedAuthority(Integer roleId) {
        return authorityMapper.selectUnAssignAuthority(roleId);
    }

    @Override
    public void saveRoleAssignAuthority(Integer roleId, List<Integer> authorityIds) {
        // 为了简化操作：先根据roleId删除中间表inner_role_authority中旧的数据，再根据authorityIds保存全部新的数据
        // 1.根据roleId删除旧的关联关系数据
        authorityMapper.deleteRoleAuthorityRelationship(roleId);

        // 2.如果authorityIds不为空，就是有分配到权限，根据authorityIds和roleId保存新的关联关系
        if(authorityIds != null && authorityIds.size() > 0) {
            authorityMapper.insertRoleAuthorityRelationship(roleId, authorityIds);
        }
    }

    @Override
    public List<String> getAssignedAuthorityNameByUserId(Integer userId) {
        return authorityMapper.selectAssignedAuthorityNameByUserId(userId);
    }
}
