package com.kaka.service.Impl;

import com.kaka.constant.CodeType;
import com.kaka.mapper.UserMapper;
import com.kaka.model.User;
import com.kaka.service.UserService;
import com.kaka.utils.DataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    // 实现 UserService 接口中的方法，检查用户名是否存在
    @Override
    public boolean userNameIsExist(String username) {
        // 通过 UserMapper 调用数据库查询方法，根据用户名查询用户信息
        User user = userMapper.userNameIsExist(username);
        // 如果查询结果不为 null，表示用户名已存在，返回 true；否则返回 false。
        return user != null;
    }

    // 实现 UserService 接口中的方法，插入用户数据到数据库
    @Override
    @Transactional
    public DataMap insertUser(User user) {
        // 判断用户名是否异常：1. 用户名长度是否超过35个字符，2. 是否包含特殊字符
        if (user.getUsername().length() > 35) {
            return DataMap.fail(CodeType.USERNAME_FORMAT_ERROR);
        }

        // 判断手机号码是否已存在
        User userIsExist = userMapper.phoneIsExist(user.getPhone());

        if (userIsExist != null) {
            return DataMap.fail(CodeType.PHONE_EXIST);
        }

        // 设置默认头像，根据性别不同设置不同的头像
        if ("male".equals(user.getGender())) {
            user.setAvatarImgUrl("www.javatiaocao.com");
        } else {
            user.setAvatarImgUrl("www.javatiaocao.com");
        }

        // 设置truename（此处设置为固定值）
        user.setTrueName("www.javatiaocao.com");


        // 调用 UserMapper 插入用户信息到数据库
        userMapper.insertUser(user);
        //当注册成功的时候，配置权限角色,默认是普通用户
        User userByPhone = userMapper.findUserByPhone(user.getPhone());
        updateRoleByUserId(userByPhone.getId(),1);

        // 返回成功的 DataMap 对象
        return DataMap.success();
    }

    private void updateRoleByUserId(int userId,int roleId) {
            UserMapper.updateRoleByUserId(userId,roleId);

    }

    @Override
    public User findUserPhone(String Phone) {

     return userMapper.findUserByPhone(Phone);
    }
}
