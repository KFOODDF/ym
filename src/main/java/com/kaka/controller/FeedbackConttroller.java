package com.kaka.controller;

import com.kaka.constant.CodeType;
import com.kaka.service.FeedBackService;
import com.kaka.utils.DataMap;
import com.kaka.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class FeedbackConttroller {
    @Autowired
    private FeedBackService feedBackService;

    @GetMapping (value = "/getAllFeedback")
    public String getAllFeedback(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {
        try {
            // 从文章服务中获取文章管理信息
            DataMap data = feedBackService.getAllFeedback(rows, pageNum);
            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("FeedbackConttroller getAllFeedback Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

        //查询所有的悄悄话
    @PostMapping(value = "/getAllPrivateWord")
    public String getAllPrivateWord() {
        try {
            // 从文章服务中获取文章管理信息
            DataMap data = feedBackService.getAllPrivateWord();
            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("FeedbackConttroller getAllPrivateWord Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}
