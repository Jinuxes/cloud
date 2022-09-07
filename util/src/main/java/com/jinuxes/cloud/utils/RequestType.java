package com.jinuxes.cloud.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 判断请求是普通请求还是Ajax请求的工具类
 */
public class RequestType {

    /**
     * 判断请求是Ajax还是普通请求
     * @param httpServletRequest http请求
     * @return true是Ajax请求，false是普通请求
     */
    public static boolean judgeRequestType(HttpServletRequest httpServletRequest){
        String acceptHeader = httpServletRequest.getHeader("Accept");
        String xRequestedWithHeader = httpServletRequest.getHeader("X-Requested-With");

        return (acceptHeader!=null && acceptHeader.contains("application/json"))
                || (xRequestedWithHeader!=null && xRequestedWithHeader.contains("XMLHttpRequest"));
    }
}
