package com.jinuxes.cloud.mvc.handler;

import com.google.gson.Gson;
import com.jinuxes.cloud.utils.CloudConstant;
import com.jinuxes.cloud.utils.RequestType;
import com.jinuxes.cloud.utils.ResultEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("customAccessDeniedHandler")
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private String errorPage;  // 错误页面，注入bean的时候一定要调用setErrorPage去设置，否则报错

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        // 判断是Ajax请求还是普通请求
        boolean isAjax = RequestType.judgeRequestType(httpServletRequest);
        if(isAjax){
            ResultEntity resultEntity = ResultEntity.failed(CloudConstant.FORBIDDENMESSAGE);
            Gson gson = new Gson();
            String json = gson.toJson(resultEntity);
            httpServletResponse.getWriter().write(json);
        }else if(!httpServletResponse.isCommitted()){  // 如果数据还没输出到客户端
            if(errorPage != null){
                httpServletRequest.setAttribute(CloudConstant.EXCEPTIONREQUESTDOMAINKEY,e);  // 将异常对象设置到请求域
                // System.out.println(e.getMessage()); // 输出：不允许访问
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 设置响应状态码403：禁止访问
                RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher(errorPage);  // 请求转发
                dispatcher.forward(httpServletRequest,httpServletResponse);
            }else{
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
            }
        }
    }

    @Value("/WEB-INF/error.jsp")
    public void setErrorPage(String errorPage){
        if((errorPage != null) && !errorPage.startsWith("/")){
            throw new IllegalArgumentException("errorPage must begin with '/'");
        }
        this.errorPage = errorPage;
    }
}
