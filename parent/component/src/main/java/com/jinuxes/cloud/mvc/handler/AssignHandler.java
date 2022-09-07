package com.jinuxes.cloud.mvc.handler;

import com.jinuxes.cloud.entity.Authority;
import com.jinuxes.cloud.entity.Role;
import com.jinuxes.cloud.service.api.AuthorityService;
import com.jinuxes.cloud.service.api.RoleService;
import com.jinuxes.cloud.service.api.UserService;
import com.jinuxes.cloud.utils.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AssignHandler {

    // @Autowired
    // UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    AuthorityService authorityService;

    /**
     * 查询角色分配信息
     */
    @RequestMapping("/assign/role/info")
    @ResponseBody
    public ResultEntity<Map<String, List<Role>>> getUserAssignRoleInfo(Integer userId){
        // 1、查询已分配角色
        List<Role> assignedRoleList = roleService.getAssignedRole(userId);

        // 2、查询未分配角色
        List<Role> unAssignRoleList = roleService.getUnAssignedRole(userId);

        // 3、将查询到的数据封装成Map返回
        Map<String,List<Role>> resultMap = new HashMap<>();
        resultMap.put("assignedRoleList",assignedRoleList);
        resultMap.put("unAssignRoleList",unAssignRoleList);
        return ResultEntity.successWithData(resultMap);
    }

    /**
     * 保存角色分配信息
     */
    @RequestMapping("/assign/role/save")
    @ResponseBody
    public ResultEntity<String> saveUserAssignRole(@RequestParam("userId") Integer userId,
                                                   @RequestParam(value="roleIds",required = false) List<Integer> roleIds){
        roleService.saveUserAssignRole(userId, roleIds);
        return ResultEntity.successWithoutData();
    }

    /**
     * 查询权限分配信息
     */
    @RequestMapping("/assign/authority/info")
    @ResponseBody
    public ResultEntity<Map<String, List<Authority>>> getRoleAssignAuthorityInfo(Integer roleId){
        // 1.查询已分配权限信息
        List<Authority> assignedAuthorityList = authorityService.getAssignedAuthority(roleId);

        // 2.查询未分配权限信息
        List<Authority> unAssignAuthorityList = authorityService.getUnAssignedAuthority(roleId);

        // 3、将查询到的数据封装成Map返回
        Map<String,List<Authority>> resultMap = new HashMap<>();
        resultMap.put("assignedAuthorityList",assignedAuthorityList);
        resultMap.put("unAssignAuthorityList",unAssignAuthorityList);
        return ResultEntity.successWithData(resultMap);
    }

    /**
     * 保存权限分配信息
     */
    @RequestMapping("/assign/authority/save")
    @ResponseBody
    public ResultEntity<String> saveRoleAssignAuthority(@RequestParam("roleId") Integer roleId,
                                                   @RequestParam(value="authorityIds",required = false) List<Integer> authorityIds){
        authorityService.saveRoleAssignAuthority(roleId, authorityIds);
        return ResultEntity.successWithoutData();
    }
}
