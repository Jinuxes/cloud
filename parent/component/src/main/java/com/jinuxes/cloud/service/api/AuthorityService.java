package com.jinuxes.cloud.service.api;

import com.github.pagehelper.PageInfo;
import com.jinuxes.cloud.entity.Authority;

import java.util.List;

public interface AuthorityService {
    // 测试用
    void save(Authority authority);

    PageInfo<Authority> getAuthorityPageInfo(Integer pageNum, Integer pageSize, String keyword);

    void saveAuthority(Authority authority);

    Authority getAuthorityByAuthorityName(String name);

    void deleteAuthoritysById(List<Integer> authorityIdList);

    void updateAuthority(Authority authority);

    List<Authority> getAssignedAuthority(Integer roleId);

    List<Authority> getUnAssignedAuthority(Integer roleId);

    void saveRoleAssignAuthority(Integer roleId, List<Integer> authorityIds);

    List<String> getAssignedAuthorityNameByUserId(Integer userId);
}
