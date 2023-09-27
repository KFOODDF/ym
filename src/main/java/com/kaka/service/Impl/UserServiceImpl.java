package com.kaka.service.Impl;

import com.kaka.constant.CodeType;
import com.kaka.mapper.UserMapper;
import com.kaka.model.User;
import com.kaka.service.UserService;
import com.kaka.utils.DataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service  // 标记这是一个Spring服务类
public class UserServiceImpl implements UserService {

    @Autowired  // 自动注入UserMapper对象
    private UserMapper userMapper;

    // 重写UserService接口中的方法，用于检查用户名是否已存在
    @Override
    public boolean userNameIsExist(String username) {
        // 调用userMapper中的方法，根据用户名查询用户信息
        User user = userMapper.userNameIsExist(username);
        // 如果查询到的用户不为null，说明用户名已存在，返回true，否则返回false
        return user != null;
    }

    // 重写UserService接口中的方法，用于插入新的用户数据到数据库
    @Override
    @Transactional  // 标记这个方法是一个事务方法
    public DataMap insertUser(User user) {
        // 检查用户名的长度是否超过35个字符，如果是，则返回一个错误的DataMap对象
        if (user.getUsername().length() > 35) {
            return DataMap.fail(CodeType.USERNAME_FORMAT_ERROR);
        }

        // 调用userMapper中的方法，根据手机号查询用户信息
        User userIsExist = userMapper.phoneIsExist(user.getPhone());

        // 如果查询到的用户不为null，说明手机号已被注册，返回一个错误的DataMap对象
        if (userIsExist != null) {
            return DataMap.fail(CodeType.PHONE_EXIST);
        }

        // 根据用户的性别设置默认的头像URL
        if ("male".equals(user.getGender())) {
            user.setAvatarImgUrl("www.javatiaocao.com");
        } else {
            user.setAvatarImgUrl("www.javatiaocao.com");
        }

        // 设置用户的真实姓名（此处设置为一个固定值）
        user.setTrueName("www.javatiaocao.com");

        // 调用userMapper中的方法，将用户信息插入到数据库
        userMapper.insertUser(user);

        // 注册成功后，根据用户的手机号查询用户信息
        User userByPhone = userMapper.findUserByPhone(user.getPhone());

        // 更新用户的角色信息，设置为普通用户（角色ID为1）
        updateRoleByUserId(userByPhone.getId(),1);

        // 返回一个表示成功的DataMap对象
        return DataMap.success();
    }

    // 私有方法，用于更新用户的角色信息
    private void updateRoleByUserId(int userId,int roleId) {
        // 调用userMapper中的方法，更新用户的角色信息
        UserMapper.updateRoleByUserId(userId,roleId);
    }

    // 重写UserService接口中的方法，根据手机号查询用户信息
    @Override
    public User findUserPhone(String Phone) {
        // 调用userMapper中的方法，根据手机号查询用户信息
        return userMapper.findUserByPhone(Phone);
    }
}
