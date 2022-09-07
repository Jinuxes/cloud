package com.jinuxes.cloud.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.jinuxes.cloud.entity.Role;
import com.jinuxes.cloud.service.api.RoleService;
import com.jinuxes.cloud.utils.DateUtil;
import com.jinuxes.cloud.utils.ResultEntity;
import com.jinuxes.cloud.utils.ValidatorGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class RoleHandler {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/role")
    public String rolePage(){
        return "role";
    }

    @RequestMapping("/role/info")
    @ResponseBody
    public ResultEntity<PageInfo<Role>> getRolePageInfo(
            @RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
            @RequestParam(value="keyword", defaultValue="") String keyword
    ){
        PageInfo<Role> pageInfo = roleService.getRolePageInfo(pageNum,pageSize,keyword);
        return ResultEntity.successWithData(pageInfo);
    }

    @RequestMapping("/role/save")
    @ResponseBody
    public ResultEntity<String> saveRole(@Validated Role role, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            // 校验失败，返回错误信息
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            return ResultEntity.failed(fieldError.getDefaultMessage());
        }else{
            // 校验成功
            role.setCreateTime(DateUtil.getCurrentDateTime());
            roleService.saveRole(role);
            return ResultEntity.successWithoutData();
        }
    }

    @RequestMapping("/role/delete")
    @ResponseBody
    public ResultEntity<String> deleteRolesById(Integer[] roleIds){
        List<Integer> roleIdList = Arrays.asList(roleIds);
        roleService.deleteRolesById(roleIdList);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("role/update")
    @ResponseBody
    public ResultEntity<String> updateRoleById(@Validated Role role, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            // 检验失败，返回错误信息
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            return ResultEntity.failed(fieldError.getDefaultMessage());
        }else{
            // 校验成功
            roleService.updateRole(role);
            return ResultEntity.successWithoutData();
        }
    }
}
