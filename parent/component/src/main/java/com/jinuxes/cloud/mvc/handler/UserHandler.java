package com.jinuxes.cloud.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.jinuxes.cloud.entity.File;
import com.jinuxes.cloud.entity.SecurityUserDetail;
import com.jinuxes.cloud.entity.User;
import com.jinuxes.cloud.service.api.FileService;
import com.jinuxes.cloud.service.api.UserService;
import com.jinuxes.cloud.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserHandler {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        return "redirect:/login/page";
    }

    @RequestMapping("/login/page")
    public String loginPage(){
        return "login";
    }

    @RequestMapping("/main")
    public String mainPage(){
        return "main";
    }

    @RequestMapping("/user")
    public String userPage(){
        return "user";
    }

    // 获取用户分页信息
    @RequestMapping("/user/info")
    @ResponseBody
    public ResultEntity<PageInfo<User>> getUserPageInfo(
            @RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
            @RequestParam(value="keyword", defaultValue="") String keyword
    ){
        PageInfo<User> pageInfo = userService.getUserPageInfo(pageNum,pageSize,keyword);
        return ResultEntity.successWithData(pageInfo);
    }

    // 保存用户
    @RequestMapping("/user/save")
    @ResponseBody
    public ResultEntity<String> saveUser(@Validated(ValidatorGroups.AddUser.class) User user, BindingResult bindingResult, HttpSession session){
        if(bindingResult.hasErrors()){
            // 校验失败，返回错误信息
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            return ResultEntity.failed(fieldError.getDefaultMessage());
        }else{
            // 校验成功
            String currentDateTime = DateUtil.getCurrentDateTime();
            user.setCreateTime(currentDateTime);
            userService.saveUser(user, session);

            return ResultEntity.successWithoutData();
        }
    }

    @RequestMapping("/user/delete")
    @ResponseBody
    public ResultEntity<String> deleteUsersById(Integer[] userIds){
        List<Integer> userIdList = Arrays.asList(userIds);
        userService.deleteUsersById(userIdList);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/user/update")
    @ResponseBody
    public ResultEntity<String> updateUserByAccount(@Validated(ValidatorGroups.UpdateUser.class) User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            // 检验失败，返回错误信息
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            return ResultEntity.failed(fieldError.getDefaultMessage());
        }else{
            // 校验成功
            userService.updateUser(user);
            return ResultEntity.successWithoutData();
        }
    }

    // private String getPersonHomePath(HttpSession httpSession, String account){
    //     ServletContext servletContext = httpSession.getServletContext();
    //     String realPath = servletContext.getRealPath(File.separator);
    //     String filesRootPath = realPath+"\\"+"files";
    //
    //     // 获取用户账号名，作为用户的根目录（家目录）名
    //     // SecurityContext securityContext = (SecurityContext)httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
    //     // Authentication authentication = securityContext.getAuthentication();
    //     // SecurityUserDetail securityUserDetail = (SecurityUserDetail) authentication.getPrincipal();
    //     // User originalUser = securityUserDetail.getOriginalUser();
    //     // System.out.println(originalUser);
    //
    //
    //     String personHomePath = filesRootPath + "\\" + account;
    //     System.out.println(personHomePath);
    //     return personHomePath;
    // }
}
