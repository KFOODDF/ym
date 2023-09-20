package com.kaka.controller;

import com.kaka.constant.CodeType;
import com.kaka.model.User;
import com.kaka.service.UserService;
import com.kaka.utils.DataMap;
import com.kaka.utils.JsonResult;
import com.kaka.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j // 使用Lombok库的@Slf4j注解为类提供一个日志对象
@RestController // 声明这是一个Spring REST控制器
public class RegisterController {

    @Autowired // 自动装配UserService到这个类中
    private UserService userService;

    // HTTP GET请求的处理方法，用于检查电话号码是否存在
    @GetMapping(value = "/phone")
    public String phoneIsExist(String phone){
        User user = userService.findUserPhone(phone); // 通过服务层查找用户
        return user.toString(); // 返回用户对象的字符串表示形式
    }

    // HTTP POST请求的处理方法，用于注册新用户
    @PostMapping(value = "/register")
    public String register(User user, HttpServletRequest request) {
        try {
            // 处理控制层数据
            // 检查用户名是否已注册
            if (userService.userNameIsExist(user.getUsername())){
                return JsonResult.fail(CodeType.USERNAME_EXIST).toJSON(); // 如果用户名已存在，则返回失败响应
            }
            // 对密码进行加密
            MD5Util md5Util = new MD5Util();
            user.setPassword(md5Util.encode(user.getPassword())); // 使用MD5工具类对用户密码进行加密

            // 注册新用户
            DataMap data = userService.insertUser(user); // 调用服务层的插入用户方法
            return JsonResult.build(data).toJSON(); // 返回成功的JSON响应

        } catch (Exception e){
            // 如果捕获到异常，则记录异常（当前是注释掉的）
            // log.error("RegisterController register Exception", user, e);
        }
        // 如果出现任何错误，返回服务器异常响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
}
