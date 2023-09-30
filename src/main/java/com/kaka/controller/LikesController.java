package com.kaka.controller;

import com.kaka.constant.CodeType;
import com.kaka.service.LikesService;
import com.kaka.utils.DataMap;
import com.kaka.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//点赞管理控制层
@RestController
@Slf4j
public class LikesController {
        @Autowired
        private LikesService likesService;

    //点赞列表接口
            @PostMapping(value = "/getArticleThumbsUp")
            public String getArticleThumbsUp(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {
                try {
                    // 从文章服务中获取文章管理信息
                    DataMap data = likesService.getArticleThumbsUp(rows, pageNum);
                    // 构建并返回成功的JSON响应
                    return JsonResult.build(data).toJSON();
                } catch (Exception e) {
                    // 记录异常信息
                    log.error("LikesController getArticleThumbsUp Exception", e);
                }
                // 如果发生错误，返回服务器异常的JSON响应
                return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
            }
}
