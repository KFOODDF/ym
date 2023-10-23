package com.kaka.controller;

import com.kaka.constant.CodeType;
import com.kaka.service.Impl.RedisServiceImpl;
import com.kaka.service.StatisticsService;
import com.kaka.utils.DataMap;
import com.kaka.utils.JsonResult;
import com.kaka.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

//统计模块！
// 使用Lombok库的@Slf4j注解为类提供一个日志对象
@Slf4j
// 声明这是一个Spring REST控制器
@RestController
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
   private RedisServiceImpl redisServiceImpl;
    @GetMapping(value = "/getVisitorNumByPageName")
 public  String   getVisitorNumByPageName(HttpServletRequest request, @RequestParam(value = "pageName") String pageName){
        try {
            // 从点赞服务中删除指定ID的友情链接
            int index = pageName.indexOf("/");
            if (index == -1){
                pageName = "visitorVolume";

            }
           DataMap data= statisticsService.getVisitorNumByPageName(request,pageName);
                return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("StatisticsController getVisitorNumByPageName Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }
    //完成仪表盘统计模块！
    @GetMapping(value = "/getStatisticsInfo")
    public  String   getStatisticsInfo(HttpServletRequest request){
        try {


            // 从点赞服务中删除指定ID的友情链接
            DataMap data= statisticsService.getStatisticsInfo(request);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("StatisticsController getStatisticsInfo Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }

 }

