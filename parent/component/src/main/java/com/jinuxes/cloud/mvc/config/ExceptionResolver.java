package com.jinuxes.cloud.mvc.config;

import com.google.gson.Gson;
import com.jinuxes.cloud.exception.AccountAlreadyUseException;
import com.jinuxes.cloud.exception.AuthorityNameAlreadyExistException;
import com.jinuxes.cloud.exception.RoleAlreadyExistException;
import com.jinuxes.cloud.utils.CloudConstant;
import com.jinuxes.cloud.utils.RequestType;
import com.jinuxes.cloud.utils.ResultEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionResolver {

    /**
     * 拒绝访问异常处理
     */
    @ExceptionHandler(value=AccessDeniedException.class)
    public ModelAndView resolveAccessDeniedException(AccessDeniedException exception,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) throws IOException {
        return resolve("error",exception,request,response);
    }

    /**
     * 账号已被注册使用异常处理
     */
    @ExceptionHandler(value=AccountAlreadyUseException.class)
    public ModelAndView resolveAccountAlreadyUseException(AccountAlreadyUseException exception,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) throws IOException {
        return resolve("error",exception,request,response);
    }

    /**
     * 角色已存在异常处理
     */
    @ExceptionHandler(value=RoleAlreadyExistException.class)
    public ModelAndView resolveRoleAlreadyExistException(RoleAlreadyExistException exception,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response) throws IOException {
        return resolve("error",exception,request,response);
    }

    /**
     * 权限已存在异常处理
     */
    @ExceptionHandler(value= AuthorityNameAlreadyExistException.class)
    public ModelAndView resolveAuthorityNameAlreadyExistException(AuthorityNameAlreadyExistException exception,
                                                         HttpServletRequest request,
                                                         HttpServletResponse response) throws IOException {
        return resolve("error",exception,request,response);
    }


    // /**
    //  * 运行时异常处理，测试用
    //  */
    // @ExceptionHandler(RuntimeException.class)
    // public ModelAndView resolveRuntimeException(RuntimeException exception,
    //                                                  HttpServletRequest request,
    //                                                  HttpServletResponse response) throws IOException {
    //     return resolve("error",exception,request,response);
    // }

    private ModelAndView resolve(String viewName,
                                 Exception exception,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        if(RequestType.judgeRequestType(request)){
            ResultEntity resultEntity = ResultEntity.failed(exception.getMessage());
            Gson gson = new Gson();
            String json = gson.toJson(resultEntity);
            response.getWriter().write(json);
            // Ajax请求，不用返回页面，所以使用原始的response对象返回json数据，方法返回null即可
            return null;
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CloudConstant.EXCEPTIONREQUESTDOMAINKEY, exception);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
