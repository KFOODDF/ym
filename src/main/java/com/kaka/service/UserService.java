package com.kaka.service;

import com.kaka.model.User;
import com.kaka.utils.DataMap;
import org.springframework.stereotype.Service;

@Service
public interface UserService  {

//判断用户名是否存在
    boolean userNameIsExist(String username);
            //注册用户
    DataMap insertUser(User user);

    //通过用户手机号查找用户
    User findUserPhone(String Phone);

    DataMap getUserPersonalInfo(String username);
}
