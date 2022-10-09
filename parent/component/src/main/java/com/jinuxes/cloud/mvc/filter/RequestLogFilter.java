package com.jinuxes.cloud.mvc.filter;

import com.google.gson.Gson;
import com.jinuxes.cloud.entity.RequestLog;
import com.jinuxes.cloud.entity.SecurityUserDetail;
import com.jinuxes.cloud.mapper.RequestLogMapper;
import com.jinuxes.cloud.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import com.jinuxes.cloud.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class RequestLogFilter implements Filter {

    @Autowired
    private RequestLogMapper requestLogMapper;

    // public void setRequestLogMapper(RequestLogMapper requestLogMapper) {
    //     this.requestLogMapper = requestLogMapper;
    // }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        // 访问时间
        String createTime = DateUtil.getCurrentDateTime();
        // 请求类型
        String method = httpServletRequest.getMethod();
        // 请求路径
        String requestPath = httpServletRequest.getServletPath();
        // URL
        String requestUrl = String.valueOf(httpServletRequest.getRequestURL());
        // ip
        String ipAddress = httpServletRequest.getRemoteAddr();
        // session
        HttpSession httpSession = httpServletRequest.getSession();
        // 获取spring security存放到session中的信息。如果没有登录，那么将会是一个null
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        // // 获取account
        String account = null;
        if(securityContextImpl != null){
            User user = ((SecurityUserDetail)(securityContextImpl.getAuthentication().getPrincipal())).getOriginalUser();
            account = user.getAccount();
        }
        // 获取请求参数字符串
        Iterator iterator = servletRequest.getParameterMap().entrySet().iterator();
        StringBuilder paramString = new StringBuilder();
        while(iterator.hasNext()){
            Map.Entry<String, Object[]> entry = (Map.Entry)iterator.next();
            Gson gson = new Gson();
            // 如果参数是密码，这将值值空（就是不将密码保存到请求日志中）
            if(entry.getKey().equals("password")){
                paramString.append(entry.getKey()).append("=").append("[]");
            }else{
                paramString.append(entry.getKey()).append("=").append(gson.toJson(entry.getValue()));
            }
            if(iterator.hasNext()){
                paramString.append(", ");
            }
        }
        if(paramString.length() == 0){
            paramString = null;
        }

        RequestLog requestLog = new RequestLog();
        requestLog.setCreateTime(createTime);
        requestLog.setMethod(method);
        requestLog.setIpAddress(ipAddress);
        requestLog.setRequestPath(requestPath);
        requestLog.setRequestUrl(requestUrl);
        requestLog.setAccount(account);
        requestLog.setParameter(String.valueOf(paramString));
        requestLogMapper.insertSelective(requestLog);
        filterChain.doFilter(servletRequest,servletResponse);
        // 这后面可以添加对response的处理，也可以将response的结果内容写入到数据库。形成完整的request->response日志数据。
    }

    @Override
    public void destroy() {

    }
}
