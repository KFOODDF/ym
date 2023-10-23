package com.kaka.controller;

// 导入相关的类和包

// 导入常量类
import com.kaka.constant.CodeType;
// 导入友情链接模型
import com.kaka.model.ArticleLikesRecord;
import com.kaka.model.Friendlink;
// 导入点赞服务接口
import com.kaka.redis.RedisService;
import com.kaka.service.Impl.RedisServiceImpl;
import com.kaka.service.LikesService;
// 导入数据映射工具类
import com.kaka.service.UserService;
import com.kaka.utils.DataMap;
// 导入JSON结果工具类
import com.kaka.utils.JsonResult;
// 导入字符串工具类
import com.kaka.utils.StringUtil;
// 导入Lombok的Value注解
import com.kaka.utils.TimeUtil;
import lombok.Value;
// 导入Lombok的日志注解
import lombok.extern.slf4j.Slf4j;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring的事务注解
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
// 导入Spring的REST控制器注解
import org.springframework.web.bind.annotation.*;

// 导入HttpServletRequest类
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 点赞管理控制器，用于处理与点赞相关的请求。
 */
// 定义为RESTful控制器
@RestController
// 使用Lombok提供的日志功能
@Slf4j
public class LikesController {

    // 自动注入点赞服务
    @Autowired
    private LikesService likesService;
    @Autowired
    private UserService userService;
    @Autowired
   private RedisServiceImpl redisServiceImpl;

        //新增点赞！
    @GetMapping(value = "/addArticleLike")
    public String addArticleLike(@RequestParam(value = "articleId") String articleId, @AuthenticationPrincipal Principal principal, HttpServletRequest request) {
        try {
            //获取username
            Principal userPrincipal = request.getUserPrincipal();
            String name = userPrincipal.getName();
            //判断当前用户是否已经点赞
            if (likesService.isLiked(Long.valueOf(articleId),name)){
               return JsonResult.fail(CodeType.ARTICLE_HAS_THUMBS_UP).toJSON();
            }
            // 从点赞服务中获取文章点赞信息
            DataMap data = likesService.updateLikeByArticleId(articleId);
            ArticleLikesRecord articleLikesRecord = new ArticleLikesRecord(Long.valueOf(articleId),userService.getUserIdByuserName(name),new TimeUtil().getFormatDateForFive());
            likesService.inertArticleLikesRecord(articleLikesRecord);
            //更新redis中的数据
            redisServiceImpl.readThumbsUpOnRedis(StringUtil.ARTICLE_THUMBS_UP,1);


            // 构建并返回成功的JSON响应

            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("LikesController addArticleLike Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }







    /**
     * 获取文章点赞列表。
     *
     * @param rows 每页显示的记录数。
     * @param pageNum 当前页码。
     * @return 返回点赞列表的JSON响应。
     */
    // 定义POST请求映射
    @PostMapping(value = "/getArticleThumbsUp")
    public String getArticleThumbsUp(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {
        try {
            // 从点赞服务中获取文章点赞信息
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

    /**
     * 将所有点赞消息标记为已读。
     *
     * @return 返回操作结果的JSON响应。
     */
    // 定义GET请求映射
    @GetMapping(value = "/readAllThumbsUp")
    public String readAllThumbsUp() {
        try {
            // 从点赞服务中标记所有点赞消息为已读
            DataMap data = likesService.readAllThumbsUp();
            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("LikesController readAllThumbsUp Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获取友情链接列表。
     *
     * @return 返回友情链接列表的JSON响应。
     */
    // 定义POST请求映射
    @PostMapping(value = "/getFriendLink")
    public String getFriendLink() {
        try {
            // 从点赞服务中获取友情链接列表
            DataMap data = likesService.getFriendLink();
            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("LikesController getFriendLink Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 添加或更新友情链接。
     *
     * @param id 友情链接ID。
     * @param blogger 博主名称。
     * @param url 友情链接URL。
     * @return 返回操作结果的JSON响应。
     */
    // 使用事务注解，确保操作的原子性
    @Transactional
    // 定义POST请求映射
    @PostMapping(value = "/updateFriendLink")
    public String addOrupdateFriendLink(@RequestParam(value = "id") String id,
                                        @RequestParam(value = "blogger") String blogger,
                                        @RequestParam(value = "url") String url) {
        try {
            DataMap data;
            // 创建友情链接对象
            Friendlink friendlink = new Friendlink(blogger, url);
            // 判断id是否为空，为空则添加，否则更新
            if (StringUtil.BLANK.equals(id)) {
                data = likesService.addFriendLink(friendlink);
            } else {
                data = likesService.updateFriendLink(friendlink);
            }

            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("LikesController updateFriendLink Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 根据ID删除友情链接。
     *
     * @param id 友情链接ID。
     * @return 返回操作结果的JSON响应。
     */
    // 定义POST请求映射
    @PostMapping(value = "/deleteFriendLink")
    public String deleteFriendLink(@RequestParam(value = "id") String id) {
        try {
            // 从点赞服务中删除指定ID的友情链接
            DataMap data = likesService.deleteFriendLink(id);
            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("LikesController deleteFriendLink Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


//首页的友情链接模块
    @PostMapping(value = "/getFriendLinkInfo")
    public String getFriendLinkInfo() {
        try {
            // 从点赞服务中获取友情链接列表
            DataMap data = likesService.getFriendLink();
            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("LikesController getFriendLinkInfo Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
}
