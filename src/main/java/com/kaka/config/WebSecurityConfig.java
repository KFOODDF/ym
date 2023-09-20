package com.kaka.config;

import com.kaka.service.Impl.UserDetailServiceImpl;
import com.kaka.utils.MD5Util;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security配置类
 */
@Configuration  // 标识这是一个配置类，Spring Boot在启动时会自动加载这个类
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 启用方法安全性设置
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {  // 继承WebSecurityConfigurerAdapter类，以便自定义安全配置

    // 注册自定义的UserDetailsService实现类
    @Bean
    // 标识这是一个Bean，Spring会自动管理这个Bean
    UserDetailsService userDetailsServiceImpl() {
        return new UserDetailServiceImpl();  // 创建一个UserDetailServiceImpl对象
    }

    @Override  // 重写父类的方法
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置认证管理器，指定UserDetailsService和密码加密方式
        auth.userDetailsService(userDetailsServiceImpl()).passwordEncoder(new PasswordEncoder() {
            MD5Util md5Util = new MD5Util();  // 创建一个MD5Util对象，用于密码的加密和匹配

            @Override  // 重写encode方法，用于加密密码
            public String encode(CharSequence rawPassword) {
                return md5Util.encode((String) rawPassword);  // 对原始密码进行MD5加密
            }

            @Override  // 重写matches方法，用于验证密码
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(md5Util.encode((String) rawPassword));  // 验证原始密码和已加密密码是否匹配
            }
        });
    }

    @Override  // 重写父类的方法
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()  // 配置URL访问权限
                .antMatchers("/", "/index", "/aboutme", "/archives", "/categories", "/friendlylink", "/tags", "/update")
                .permitAll()  // 以上URL路径允许所有用户访问
                .antMatchers("/editor", "/user").hasAnyRole("USER")  // "/editor"和"/user"路径只允许USER角色访问
                .antMatchers("/ali", "/mylove").hasAnyRole("ADMIN")  // "/ali"和"/mylove"路径只允许ADMIN角色访问
                .antMatchers("/superadmin", "/myheart", "/today", "/yesterday").hasAnyRole("SUPERADMIN")  // 同上，只允许SUPERADMIN角色访问
                .and()
                .formLogin()  // 配置表单登录
                .loginPage("/login")  // 指定登录页面的URL
                .failureUrl("/login?error")  // 登录失败后跳转的URL
                .defaultSuccessUrl("/")  // 登录成功后默认跳转的URL
                .and()
                .headers().frameOptions().sameOrigin()  // 允许同源的iframe显示
                .and()
                .logout()  // 配置注销功能
                .logoutUrl("/logout")  // 注销URL
                .logoutSuccessUrl("/")  // 注销成功后跳转的URL
                .and()
                .csrf().disable();  // 禁用CSRF保护
    }
}
