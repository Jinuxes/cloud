package com.jinuxes.cloud.mvc.config;

import com.google.gson.Gson;
import com.jinuxes.cloud.exception.*;
import com.jinuxes.cloud.utils.CloudConstant;
import com.jinuxes.cloud.utils.RequestType;
import com.jinuxes.cloud.utils.ResultEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
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

    /**
     * 创建目录时，父路径不存在异常
     */
    @ExceptionHandler(value= ParenPathDoesNotExistException.class)
    public ModelAndView resolveParenPathDoesNotExistException(ParenPathDoesNotExistException exception,
                                                                  HttpServletRequest request,
                                                                  HttpServletResponse response) throws IOException {
        return resolve("error",exception,request,response);
    }

    /**
     * 创建目录时，目录已存在异常
     */
    @ExceptionHandler(value= DirectoryAlreadyExistsException.class)
    public ModelAndView resolveDirectoryAlreadyExistsException(DirectoryAlreadyExistsException exception,
                                                              HttpServletRequest request,
                                                              HttpServletResponse response) throws IOException {
        return resolve("error",exception,request,response);
    }

    /**
     * 目录层次结构太深异常，也就是File对象的path属性过长插入数据库时会导致超过数据库path字段设定的长度报的错误。
     */
    @ExceptionHandler(value= DirectoryHierarchyIsTooDeepException.class)
    public ModelAndView resolveDirectoryHierarchyIsTooDeepException(DirectoryHierarchyIsTooDeepException exception,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response) throws IOException {
        return resolve("error",exception,request,response);
    }

    /**
     * 文件已存在异常处理
     */
    @ExceptionHandler(value= FileAlreadyExistsException.class)
    public ModelAndView resolveFileAlreadyExistsException(FileAlreadyExistsException exception,
                                                                    HttpServletRequest request,
                                                                    HttpServletResponse response) throws IOException {
        return resolve("error",exception,request,response);
    }

    /**
     * 文件上传大小超出最大范围异常处理
     */
    @ExceptionHandler(value= FileSizeExceedsMaxUploadLimitException.class)
    public ModelAndView resolveFileSizeExceedsMaxUploadLimitException(FileSizeExceedsMaxUploadLimitException exception,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response) throws IOException {
        return resolve("error",exception,request,response);
    }

    /**
     * 文件不存在异常
     */
    @ExceptionHandler(value= FileNotExistException.class)
    public ModelAndView resolveFileNotExistException(FileNotExistException exception,
                                                                      HttpServletRequest request,
                                                                      HttpServletResponse response) throws IOException {
        return resolve("error",exception,request,response);
    }

    /**
     * 恢复文件错误异常RecoveryFileException
     */
    @ExceptionHandler(value= RecoveryFileException.class)
    public ModelAndView resolveRecoveryFileException(RecoveryFileException exception,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) throws IOException {
        return resolve("error",exception,request,response);
    }

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
