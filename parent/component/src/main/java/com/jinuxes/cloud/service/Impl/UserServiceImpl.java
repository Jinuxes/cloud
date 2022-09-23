package com.jinuxes.cloud.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinuxes.cloud.entity.File;
import com.jinuxes.cloud.entity.User;
import com.jinuxes.cloud.exception.AccountAlreadyUseException;
import com.jinuxes.cloud.mapper.FileMapper;
import com.jinuxes.cloud.mapper.UserMapper;
import com.jinuxes.cloud.service.api.UserService;
import com.jinuxes.cloud.utils.FileUtils;
import com.jinuxes.cloud.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileMapper fileMapper;

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
    public void saveUser(User user, HttpSession session) {
        User result = getUserByAccount(user.getAccount());
        if(result == null){
            // 对密码进行加密，使用的是BCryptPasswordEncoder
            String password = user.getPassword();
            user.setPassword(bCryptPasswordEncoder.encode(password));
            userMapper.insert(user);

            // 获取用户账号，及创建时间
            String account = user.getAccount();
            String createDateTime = user.getCreateTime();
            // 封装File实体类对象
            // 其中size不需要，因为目录没有大小
            // 修改时间目前暂时用不到，也为不需要封装
            // 根目录files不在t_file数据库表中登记，所以没有id，因此home目录的parent_id为null,也就不需要封装parent_id字段
            File file = new File();
            file.setFileId(UUIDUtils.getUUID());
            file.setName(account);  // home目录名就是账户名
            file.setOwner(account);  // 所有者也是账户名，其实也可以使用userId
            file.setPath("\\");  // 项目中的files目录就是文件的根路径，所以新建用户的home目录就是挂在这个files目录下的，“\”就是files目录
            file.setCreateTime(createDateTime);
            file.setIsDirectory(true);
            file.setShare(false);
            file.setTrash(0);
            file.setIsDelete(false);
            // 调用fileMapper保存创建的目录信息到数据库
            fileMapper.insertSelective(file);
            // 在真实目录中给新增的用户创建个人Home目录
            String path = getCurrentUserHomePath(session, account);
            try {
                FileUtils.makeDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    private String getCurrentUserHomePath(HttpSession session, String account){
        ServletContext servletContext = session.getServletContext();
        String realPath = servletContext.getRealPath(java.io.File.separator);
        String filesRootPath = realPath+"\\"+"files";
        String currentUserHomePath = filesRootPath + "\\" + account;
        return currentUserHomePath;
    }
}
