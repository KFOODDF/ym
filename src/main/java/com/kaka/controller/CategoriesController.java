package com.kaka.controller;

import com.kaka.constant.CodeType;
import com.kaka.service.CategoriesService;
import com.kaka.utils.DataMap;
import com.kaka.utils.JsonResult;
import com.kaka.utils.StringUtil;
import com.kaka.utils.TransCodingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Objects;

// 标记这是一个Spring RESTful Controller
@RestController
// 使用Lombok的@Slf4j注解为类提供日志功能
@Slf4j
public class CategoriesController {

    // 使用Spring的Autowired注解自动注入CategoriesService
    @Autowired
    CategoriesService categoriesService;

    // 定义一个HTTP GET方法，用于获取文章分类
    @GetMapping(value = "/getArticleCategories")
    public String getArticleCategories() {
        try {
            // 调用service层的方法获取文章分类数据
            DataMap data = categoriesService.getArticleCategories();
            // 使用JsonResult工具类构建成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 如果发生异常，使用日志记录异常信息
            log.error("categoriesService getArticleCategories Exception", e);
        }
        // 如果出现任何错误，使用JsonResult工具类构建服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    // 定义一个HTTP POST方法，用于更新文章分类
    @PostMapping(value = "/updateCategory")
    public String updateCategory(@RequestParam(value = "categoryName")String categoryName, @RequestParam(value = "type")int type) {
        try {
            // 调用service层的方法更新文章分类
            DataMap data = categoriesService.updateCategory(categoryName, type);
            // 使用JsonResult工具类构建成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 如果发生异常，使用日志记录异常信息
            log.error("CategoriesController getArticleCategories Exception", e);
        }
        // 如果出现任何错误，使用JsonResult工具类构建服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    // 定义一个HTTP GET方法，用于查找文章分类名称
    @GetMapping(value = "/findCategoriesName")
    public String findCategoriesName(){
        try {
            // 调用service层的方法查找文章分类名称
            DataMap data = categoriesService.findCategoriesNames();
            // 使用JsonResult工具类构建成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 如果发生异常，使用日志记录异常信息
            log.error("categoriesService getArticleCategories Exception", e);
        }
        // 如果出现任何错误，使用JsonResult工具类构建服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


        //博客分类列表
    @GetMapping(value = "/getCategoryArticle")
    public String getCategoryArticle(@RequestParam(value = "category")String category,@RequestParam(value = "pageNum")int pageNum,@RequestParam(value = "rows")int rows){
        try {
            if (category.equals(StringUtil.BLANK)){
                category = TransCodingUtil.unicodeToString(category);
            }
            // 调用service层的方法查找文章分类名称
            DataMap data = categoriesService.getCategoryArticle(category,rows,pageNum);
            // 使用JsonResult工具类构建成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 如果发生异常，使用日志记录异常信息
            log.error("categoriesService getCategoryArticle Exception", e);
        }
        // 如果出现任何错误，使用JsonResult工具类构建服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

//查询博客分类
    @GetMapping(value = "/findCategoriesNameAndArticleNum")
    public String findCategoriesNameAndArticleNum(){
        try {
            // 调用service层的方法查找文章分类名称
            DataMap data = categoriesService.findCategoriesNameAndArticleNum();
            // 使用JsonResult工具类构建成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 如果发生异常，使用日志记录异常信息
            log.error("categoriesService findCategoriesNameAndArticleNum Exception", e);
        }
        // 如果出现任何错误，使用JsonResult工具类构建服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

//获取博客分类里的当前留言
    @GetMapping(value = "/getPageLeaveMessage")
    public String getPageLeaveMessage(@RequestParam(value = "pageName")String pageName, @AuthenticationPrincipal Principal principal,HttpServletRequest request){
        String userName = null;
        try {
          // 1. userName = principal.getName();
            Principal userPrincipal = request.getUserPrincipal();

                if (!Objects.isNull(userPrincipal)){
                   userName = request.getUserPrincipal().getName();
                }


            // 调用service层的方法查找文章分类名称
            DataMap data = categoriesService.getPageLeaveMessage(pageName,userName);
            // 使用JsonResult工具类构建成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 如果发生异常，使用日志记录异常信息
            log.error("categoriesService getPageLeaveMessage Exception", e);
        }
        // 如果出现任何错误，使用JsonResult工具类构建服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    @PostMapping(value = "/publishLeaveMessage")
    public String publishLeaveMessage(@RequestParam(value = "leaveMessageContent")String leaveMessageContent,@RequestParam(value = "pageName")String pageName,@AuthenticationPrincipal Principal principal,HttpServletRequest request){
        String userName = null;
        try {

            Principal userPrincipal = request.getUserPrincipal();

            if (!Objects.isNull(userPrincipal)){
                userName = request.getUserPrincipal().getName();
            }



            // 提交留言

          categoriesService.publishLeaveMessage(leaveMessageContent,userName,pageName);
            //调用留言接口并且返回对应封装值封装数据
            DataMap data = categoriesService.getPageLeaveMessage(pageName,userName);
            // 使用JsonResult工具类构建成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 如果发生异常，使用日志记录异常信息
            log.error("categoriesService publishLeaveMessage Exception", e);
        }
        // 如果出现任何错误，使用JsonResult工具类构建服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}
