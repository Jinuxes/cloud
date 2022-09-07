package com.jinuxes.cloud.mapper;

import com.jinuxes.cloud.entity.Role;
import com.jinuxes.cloud.entity.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectRoleByKeyword(String keyword);

    Role selectRoleByName(String name);

    void deleteByRolesId(@Param("roleIdList")List<Integer> roleIdList);

    void updateByRoleId(Role role);

    List<Role> selectAssignedRole(Integer userId);

    List<Role> selectUnAssignRole(Integer userId);

    void deleteUserRoleRelationship(Integer userId);

    void insertUserRoleRelationship(@Param("userId")Integer userId, @Param("roleIds")List<Integer> roleIds);
}