package com.jinuxes.cloud.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.jinuxes.cloud.entity.SecurityUserDetail;
import com.jinuxes.cloud.entity.User;
import com.jinuxes.cloud.service.api.UserService;
import com.jinuxes.cloud.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
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

    @RequestMapping("/register")
    public String registerPage(){
        return "register";
    }

    @RequestMapping("/register/save")
    @ResponseBody
    public ResultEntity<String> doRegister(@Validated(ValidatorGroups.AddUser.class) User user, BindingResult bindingResult, HttpSession session){

        if(bindingResult.hasErrors()){
            // 校验失败，返回错误信息。返回第一个错误信息即可，不用全返回
            FieldError filedError = bindingResult.getFieldErrors().get(0);
            return ResultEntity.failed(filedError.getDefaultMessage());
        }else{
            // 校验成功
            String currentDateTime = DateUtil.getCurrentDateTime();
            user.setCreateTime(currentDateTime);
            userService.saveRegisterUser(user, session);
            return ResultEntity.successWithoutData();
        }
    }

    @RequestMapping("/personal/info")
    @ResponseBody
    public ResultEntity<User> getPersonalInfoByAccount(HttpSession session){
        String account = ((SecurityUserDetail)((SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getPrincipal()).getOriginalUser().getAccount();
        User user = userService.getUserByAccount(account);
        return ResultEntity.successWithData(user);
    }

    @RequestMapping("/personal/info/update")
    @ResponseBody
    public ResultEntity<String> updatePersonalInformation(@Validated(ValidatorGroups.UpdateUser.class) User user, BindingResult bindingResult, HttpSession session){
        if(bindingResult.hasErrors()){
            FieldError filedError = bindingResult.getFieldErrors().get(0);
            return ResultEntity.failed(filedError.getDefaultMessage());
        }else{
            // 在session中获取账号
            String account = ((SecurityUserDetail)((SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getPrincipal()).getOriginalUser().getAccount();
            user.setAccount(account);
            userService.updateUser(user);
            return ResultEntity.successWithoutData();
        }
    }

    @RequestMapping("/personal/password/update")
    @ResponseBody
    public ResultEntity<String> updatePersonalPassword(@Validated(ValidatorGroups.UpdateUserPassword.class) User user, BindingResult bindingResult, HttpSession session){
        if(bindingResult.hasErrors()){
            FieldError filedError = bindingResult.getFieldErrors().get(0);
            return ResultEntity.failed(filedError.getDefaultMessage());
        }else{
            // 在session中获取账号
            String account = ((SecurityUserDetail)((SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getPrincipal()).getOriginalUser().getAccount();
            user.setAccount(account);
            userService.updatePersonalPassword(user);
            return ResultEntity.successWithoutData();
        }
    }

}
