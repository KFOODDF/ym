package com.kaka.controller;

import com.kaka.constant.CodeType;
import com.kaka.redis.RedisService;
import com.kaka.service.Impl.RedisServiceImpl;
import com.kaka.service.IndexService;
import com.kaka.utils.DataMap;
import com.kaka.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Objects;

@Slf4j
@RestController
public class IndexController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private RedisServiceImpl redisServiceImpl;
    //查询首页网站基本信息！
    @GetMapping(value = "/newComment")
    public String getNewComment(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {
        try {
            // 从点赞服务中获取文章点赞信息
            DataMap data = indexService.getNewComment(rows, pageNum);
            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("IndexController getNewComment Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    @GetMapping(value = "/newLeaveWord")
    public String getNewLeaveWord(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {
        try {
            // 从点赞服务中获取文章点赞信息
            DataMap data = indexService.getNewLeaveWord(rows, pageNum);
            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("IndexController getNewLeaveWord Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

//发现标签云
    @GetMapping(value = "/findTagsCloud")
    public String findTagsCloud() {
        try {
            // 从点赞服务中获取文章点赞信息
            DataMap data = indexService.findTagsCloud();
            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("IndexController findTagsCloud Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

//获取登录用户未读信息

    @PostMapping(value = "/getUserNews")
    public String getUserNews(@AuthenticationPrincipal Principal principal, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (!Objects.isNull(userPrincipal)) {
                String username = userPrincipal.getName();
                // 从点赞服务中获取文章点赞信息
                DataMap data = redisServiceImpl.getUserNews(username);
                // 构建并返回成功的JSON响应
                return JsonResult.build(data).toJSON();
            }else {
                HashMap<String, Object> map = new HashMap<>();
                map.put("result",0);
                return JsonResult.build(DataMap.success().setData(map)).toJSON();
            }


        } catch (Exception e) {
            // 记录异常信息
            log.error("IndexController getUserNews   Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }












    @GetMapping(value = "/getSiteInfo")
    public String getArticleThumbsUp() {
        try {
            // 从点赞服务中获取文章点赞信息
            DataMap data = indexService.getSiteInfo();
            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("IndexController getSiteInfo Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


}
