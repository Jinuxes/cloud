package com.jinuxes.cloud.entity;

import com.jinuxes.cloud.utils.CloudConstant;

import javax.validation.constraints.Pattern;

public class Authority {
    private Integer id;

    @Pattern(regexp="(^[a-zA-Z0-9_:-]{2,30}$)|(^[\\u2E80-\\u9FFF]{2,10}$)",
            message= CloudConstant.AUTHORITYNAMEERROR)
    private String name;

    @Pattern(regexp="(^[a-zA-Z0-9_:-]{2,30}$)|(^[\\u2E80-\\u9FFF]{2,10}$)",
            message= CloudConstant.AUTHORITYTITLEERROR)
    private String title;

    private String createTime;

    public Authority() {
    }

    public Authority(Integer id, String name, String title, String createTime) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }
}