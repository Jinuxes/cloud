package com.jinuxes.cloud.mvc.config;

import com.jinuxes.cloud.mvc.handler.CustomAccessDeniedHandler;
import com.jinuxes.cloud.service.Impl.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.Locale;


/**
 * SpringSecurity配置类
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    @Autowired
    private AccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 注意事项：
     * 1.Spring中可以使用两个类加载资源文件：ReloadableResourceBundleMessageSource和ResourceBundleMessageSource。可配置如下messageSource这个bean id不能变
     * 2.@Bean注解注入容器的bean，默认名字就是使用方法名，所以要bean的id是messageSource，那么方法名必须是这个。
     * 3.这个是修改SpringSecurity在认证（登录）时，发生错误不显示“Bad credentials”，而是显示自定义的中文错误信息“密码错误”
     * 4.这个中文信息需要先在spring-security-core-3.0.7.RELEASE.jar下的org.springframework.security.Resource Bundle 'messages'里把messages_zh_CN.properties文件
     *   复制到项目中的resources目录下，然后修改里面AbstractUserDetailsAuthenticationProvider.badCredentials的值，然后在将messageSource（MessageSource类）注入到容器即可。
     */
    @Bean
    public MessageSource messageSource(){
        Locale.setDefault(Locale.CHINA);
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("classpath:messages_zh_CN");
        return messageSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        // // 内存方式登录
        // builder
        //         .inMemoryAuthentication()
        //         .withUser("tom")
        //         .password("123123")
        //         .roles("ADMIN");

        // 数据库方式登录
        builder
                .userDetailsService(securityUserDetailsService)
                .passwordEncoder(getPasswordEncoder())  //设置密码加密方式
                ;
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests()
                .antMatchers("/","/login/page")
                .permitAll()
                .antMatchers("/static/**")
                .permitAll()
                .antMatchers("/assign/role/info","/assign/role/save")
                .access("hasAuthority('user:assign')")
                .antMatchers("/user/delete")
                .access("hasAuthority('user:delete')")
                .antMatchers("/user/**")
                .access("hasRole('超级管理员') OR hasRole('管理员')")
                .antMatchers("/role/**","/authority/**")
                .hasRole("超级管理员")
                .anyRequest()
                .authenticated()
                // .permitAll()  // 其它所有请求允许访问，方便测试，后面再改
                .and()
                .csrf()  // 禁用跨站请求伪造功能
                .disable()
                .formLogin()  // 开启表单登录功能
                .loginPage("/login/page")  // 设置登录页地址
                .loginProcessingUrl("/login")  // 设置处理登录请求的地址
                .defaultSuccessUrl("/main",true)  //设置登录成功后重定向的地址
                // .failureUrl("/login/page")  // 登录失败跳转的页面（重定向）
                .failureForwardUrl("/login/page")  // 登录失败跳转的页面（不是重定向）
                .usernameParameter("account")  // 账号的请求参数名
                .passwordParameter("password")  // 密码的请求参数名
                .and()
                .logout()  // 开启退出登录功能
                .logoutUrl("/logout")  // 指定退出登录地址
                .logoutSuccessUrl("/login/page")  // 指定退出成功以后前往的地址
                ;

        // 异常处理
        security
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler);  //设置自定义权限不足处理程序
    }
}
