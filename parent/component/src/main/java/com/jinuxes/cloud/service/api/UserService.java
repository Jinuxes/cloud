package com.jinuxes.cloud.service.api;

import com.github.pagehelper.PageInfo;
import com.jinuxes.cloud.entity.User;

import java.util.List;

public interface UserService {
    // 测试用
    void save(User user);

    // 测试用
    // User getUserById(Integer id);

    PageInfo<User> getUserPageInfo(Integer pageNum, Integer pageSize, String keyword);

    void saveUser(User user);

    User getUserByAccount(String account);

    void deleteUsersById(List<Integer> userIdList);

    void updateUser(User user);
}
