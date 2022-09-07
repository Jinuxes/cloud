package com.jinuxes.cloud.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinuxes.cloud.entity.User;
import com.jinuxes.cloud.exception.AccountAlreadyUseException;
import com.jinuxes.cloud.mapper.UserMapper;
import com.jinuxes.cloud.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    // 测试用
    @Override
    public void save(User user) {
        userMapper.insert(user);
    }

    // 测试用
    // @Override
    // public User getUserById(Integer id) {
    //     return userMapper.selectByPrimaryKey(id);
    // }

    @Override
    public PageInfo<User> getUserPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList = userMapper.selectUserByKeyword(keyword);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @Override
    public void saveUser(User user) {
        User result = getUserByAccount(user.getAccount());
        if(result == null){
            // 对密码进行加密，使用的是BCryptPasswordEncoder
            String password = user.getPassword();
            user.setPassword(bCryptPasswordEncoder.encode(password));
            userMapper.insert(user);
        }else{
            throw new AccountAlreadyUseException("账号已被使用");
        }
    }

    @Override
    public User getUserByAccount(String account) {
        return userMapper.selectUserByAccount(account);
    }

    @Override
    public void deleteUsersById(List<Integer> userIdList) {
        userMapper.deleteByUsersId(userIdList);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateByUserAccount(user);
    }
}
