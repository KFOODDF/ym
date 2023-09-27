package com.kaka.service.Impl;

import com.kaka.constant.CodeType;
import com.kaka.mapper.UserMapper;
import com.kaka.model.Role;
import com.kaka.model.User;
import com.kaka.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service // 标记这是一个Spring的服务类
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper; // 注入UserMapper以访问数据库

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        // 通过手机号查询用户信息
        User user = userMapper.phoneIsExist(phone);

        // 如果用户不存在，抛出UsernameNotFoundException异常
        if(Objects.isNull(user)) {
            throw new UsernameNotFoundException(CodeType.USERNAME_NOT_EXIST.name());
        }

        // 如果用户存在，通过手机号查询用户的角色列表
        List<Role> roles = userMapper.queryRolesByPhone(phone);
        user.setRoles(roles); // 设置用户的角色列表


        // 记录用户登录的系统时间
        TimeUtil timeUtil = new TimeUtil();
        String formatDateForSix = timeUtil.getFormatDateForSix();

        // 更新用户最近的登录时间
        userMapper.updatRecentlyLanded(phone, formatDateForSix);

        // 创建一个用于存储用户角色权限的列表
        ArrayList<SimpleGrantedAuthority> auths = new ArrayList<>();
        for (Role role : user.getRoles()) {
            auths.add(new SimpleGrantedAuthority(role.getName())); // 为每个角色创建一个SimpleGrantedAuthority并添加到列表中
        }

        // 返回一个Spring Security的User对象，该对象包含用户名、密码和角色权限列表
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), auths);
    }
}
