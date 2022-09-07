package com.jinuxes.cloud.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.jinuxes.cloud.entity.Authority;
import com.jinuxes.cloud.service.api.AuthorityService;
import com.jinuxes.cloud.utils.DateUtil;
import com.jinuxes.cloud.utils.ResultEntity;
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
public class AuthorityHandler {

    @Autowired
    private AuthorityService authorityService;

    @RequestMapping("/authority")
    public String authorityPage(){
        return "authority";
    }

    @RequestMapping("/authority/info")
    @ResponseBody
    public ResultEntity<PageInfo<Authority>> getAuthorityPageInfo(
            @RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
            @RequestParam(value="keyword", defaultValue="") String keyword
    ){

        PageInfo<Authority> pageInfo = authorityService.getAuthorityPageInfo(pageNum,pageSize,keyword);
        return ResultEntity.successWithData(pageInfo);
    }

    // 保存权限
    @RequestMapping("/authority/save")
    @ResponseBody
    public ResultEntity<String> saveAuthority(@Validated Authority authority, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            // 校验失败，返回错误信息
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            return ResultEntity.failed(fieldError.getDefaultMessage());
        }else{
            // 校验成功
            authority.setCreateTime(DateUtil.getCurrentDateTime());
            authorityService.saveAuthority(authority);
            return ResultEntity.successWithoutData();
        }
    }

    @RequestMapping("/authority/delete")
    @ResponseBody
    public ResultEntity<String> deleteAuthoritysById(Integer[] authorityIds){
        for(int i:authorityIds){
            System.out.println(i);
        }
        List<Integer> authorityIdList = Arrays.asList(authorityIds);
        authorityService.deleteAuthoritysById(authorityIdList);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/authority/update")
    @ResponseBody
    public ResultEntity<String> updateAuthorityById(@Validated Authority authority, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            // 检验失败，返回错误信息
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            return ResultEntity.failed(fieldError.getDefaultMessage());
        }else{
            // 校验成功
            authorityService.updateAuthority(authority);
            return ResultEntity.successWithoutData();
        }
    }
}
