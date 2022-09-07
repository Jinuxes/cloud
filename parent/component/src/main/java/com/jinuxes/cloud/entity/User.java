package com.jinuxes.cloud.entity;

import com.jinuxes.cloud.utils.CloudConstant;

import javax.validation.constraints.Pattern;

import com.jinuxes.cloud.utils.ValidatorGroups;

public class User {
    private Integer id;

    @Pattern(regexp="^\\w{2,20}$",
            groups={ValidatorGroups.AddUser.class},
            message=CloudConstant.ACCOUNTERROR)
    private String account;

    @Pattern(regexp="^\\w{6,18}$",
            groups={ValidatorGroups.AddUser.class},
            message=CloudConstant.PASSWORDERROR)
    private String password;

    @Pattern(regexp="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)",
            groups={ValidatorGroups.AddUser.class,ValidatorGroups.UpdateUser.class},
            message=CloudConstant.USERNAMEERROR)
    private String userName;

    @Pattern(regexp="^([a-zA-Z0-9_\\.]{6,20})@([a-zA-Z0-9_]{2,6})\\.([a-z\\.]{2,6})$",
            groups={ValidatorGroups.AddUser.class,ValidatorGroups.UpdateUser.class},
            message= CloudConstant.EMAILERROR)
    private String email;

    private String createTime;

    public User() {
    }

    public User(Integer id, String account, String password, String userName, String email, String createTime) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }
}