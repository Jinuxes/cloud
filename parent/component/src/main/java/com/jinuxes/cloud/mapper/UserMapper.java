package com.jinuxes.cloud.mapper;

import com.jinuxes.cloud.entity.User;
import com.jinuxes.cloud.entity.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectUserByKeyword(String keyword);

    User selectUserByAccount(String account);

    void deleteByUsersId(@Param("userIdList") List<Integer> userIdList);

    void updateByUserAccount(User user);

    void updatePersonPasswordByAccount(User user);
}