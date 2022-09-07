package com.jinuxes.cloud.test;

import com.jinuxes.cloud.entity.Authority;
import com.jinuxes.cloud.entity.Role;
import com.jinuxes.cloud.entity.User;
import com.jinuxes.cloud.service.api.AuthorityService;
import com.jinuxes.cloud.service.api.RoleService;
import com.jinuxes.cloud.service.api.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mvc.xml"})
public class CloudTest {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthorityService authorityService;

    // @Test
    // public void testSave(){
    //     User user = new User(null,"Tom01","123123","汤姆","tom@qq.com",null);
    //     userService.save(user);
    // }
    //
    @Test
    public void insertUsersData(){
        for(int i=0;i<300;i++){
            User user = new User(null,"Jack"+i,"123123","杰克","jack"+i+"@qq.com","2022-08-29 18:15:51");
            userService.save(user);
        }
    }

    @Test
    public void insertRoleData(){
        for(int i=0;i<300;i++){
            Role role = new Role(null,"Role"+i,"2022-08-29 18:15:51");
            roleService.save(role);
        }
    }

    @Test
    public void insertAuthorityData(){
        for(int i=0;i<300;i++){
            Authority authority = new Authority(null,"Authority"+i,"权限"+i,"2022-09-01 13:52:45");
            authorityService.save(authority);
        }
    }
}
