package com.jinuxes.cloud.mapper;

import com.jinuxes.cloud.entity.Authority;
import com.jinuxes.cloud.entity.AuthorityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthorityMapper {
    int countByExample(AuthorityExample example);

    int deleteByExample(AuthorityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Authority record);

    int insertSelective(Authority record);

    List<Authority> selectByExample(AuthorityExample example);

    Authority selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Authority record, @Param("example") AuthorityExample example);

    int updateByExample(@Param("record") Authority record, @Param("example") AuthorityExample example);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);

    List<Authority> selectAuthorityByKeyword(String keyword);

    Authority selectAuthorityByName(String name);

    void deleteAuthoritysById(@Param("authorityIdList")List<Integer> authorityIdList);

    void updateAuthorityById(Authority authority);

    List<Authority> selectAssignedAuthority(Integer roleId);

    List<Authority> selectUnAssignAuthority(Integer roleId);

    void deleteRoleAuthorityRelationship(Integer roleId);

    void insertRoleAuthorityRelationship(@Param("roleId")Integer roleId, @Param("authorityIds")List<Integer> authorityIds);

    List<String> selectAssignedAuthorityNameByUserId(Integer userId);
}