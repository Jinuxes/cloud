package com.jinuxes.cloud.entity;

import com.jinuxes.cloud.utils.CloudConstant;
import com.jinuxes.cloud.utils.ValidatorGroups;

import javax.validation.constraints.Pattern;

public class Role {
    private Integer id;

    @Pattern(regexp="(^[a-zA-Z0-9_-]{2,30}$)|(^[\\u2E80-\\u9FFF]{2,10}$)",
            message= CloudConstant.ROLENAMEERROR)
    private String name;

    private String createTime;

    public Role() {
    }

    public Role(Integer id, String name, String createTime) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }
}