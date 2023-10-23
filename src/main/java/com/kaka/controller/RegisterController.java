package com.kaka.controller;

import com.kaka.constant.CodeType;
import com.kaka.model.User;
import com.kaka.service.UserService;
import com.kaka.utils.DataMap;
import com.kaka.utils.JsonResult;
import com.kaka.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Objects;

// 使用Lombok库的@Slf4j注解为类提供一个日志对象
@Slf4j
// 声明这是一个Spring REST控制器
@RestController
public class RegisterController {

    // 自动装配UserService到这个类中
    @Autowired
    private UserService userService;

    // HTTP GET请求的处理方法，用于检查电话号码是否存在
    @GetMapping(value = "/phone")
    public String phoneIsExist(String phone){
        // 通过服务层查找用户
        User user = userService.findUserPhone(phone);
        // 返回用户对象的字符串表示形式
        return user.toString();
    }

    // HTTP POST请求的处理方法，用于注册新用户
    @PostMapping(value = "/register")
    public String register(User user, HttpServletRequest request) {
        try {
            // 检查用户名是否已注册
            if (userService.userNameIsExist(user.getUsername())){
                // 如果用户名已存在，则返回失败响应
                return JsonResult.fail(CodeType.USERNAME_EXIST).toJSON();
            }
            // 使用MD5工具类对用户密码进行加密
            MD5Util md5Util = new MD5Util();
            user.setPassword(md5Util.encode(user.getPassword()));

            // 调用服务层的插入用户方法
            DataMap data = userService.insertUser(user);
            // 返回成功的JSON响应
            return JsonResult.build(data).toJSON();

        } catch (Exception e){
            // 如果捕获到异常，则记录异常
            log.error("RegisterController register Exception", user, e);
        }
        // 如果出现任何错误，返回服务器异常响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    @PostMapping(value = "/getUserPersonalInfo")
    public String getUserPersonalInfo(@AuthenticationPrincipal Principal principal, HttpServletRequest request) {
        try {
            // 获取当前登录用户的用户名
            Principal userPrincipal = request.getUserPrincipal();
            //String name = principal.getName();
            if (!Objects.isNull(userPrincipal)){
                String username = userPrincipal.getName();
                // 调用服务层方法获取用户个人信息
                DataMap dataMap =  userService.getUserPersonalInfo(username);

                // 返回成功的JSON响应
                return JsonResult.build(dataMap).toJSON();
            }
        } catch (Exception e){
            // 如果捕获到异常，则记录异常
            log.error("RegisterController getUserPersonalInfo Exception",  e);
        }
        // 如果出现任何错误，返回服务器异常响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
}
