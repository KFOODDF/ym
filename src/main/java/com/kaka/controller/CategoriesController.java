package com.kaka.controller;

import com.kaka.constant.CodeType;
import com.kaka.service.CategoriesService;
import com.kaka.utils.DataMap;
import com.kaka.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
