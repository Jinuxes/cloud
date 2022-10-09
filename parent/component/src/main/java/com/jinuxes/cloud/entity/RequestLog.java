package com.jinuxes.cloud.entity;

public class RequestLog {
    private Integer id;

    private String createTime;

    private String method;

    private String ipAddress;

    private String requestPath;

    private String requestUrl;

    private String account;

    private String parameter;

    public RequestLog(Integer id, String createTime, String method, String ipAddress, String requestPath, String requestUrl, String account, String parameter) {
        this.id = id;
        this.createTime = createTime;
        this.method = method;
        this.ipAddress = ipAddress;
        this.requestPath = requestPath;
        this.requestUrl = requestUrl;
        this.account = account;
        this.parameter = parameter;
    }

    public RequestLog() {
    }

    @Override
    public String toString() {
        return "RequestLog{" +
                "id=" + id +
                ", createTime='" + createTime + '\'' +
                ", method='" + method + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", requestPath='" + requestPath + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", account='" + account + '\'' +
                ", parameter='" + parameter + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath == null ? null : requestPath.trim();
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl == null ? null : requestUrl.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }
}