package com.kaka.controller;

import com.kaka.constant.CodeType;
import com.kaka.model.Article;
import com.kaka.service.ArticleService;
import com.kaka.service.TagService;
import com.kaka.service.UserService;
import com.kaka.utils.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 控制器类，处理与文章ID相关的请求。
 */
@RestController
@Slf4j
public class ArticleIdController {

    // 自动注入标签服务
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;

    // 自动注入文章服务
    @Autowired
    private ArticleService articleService;

    /**
     * 获取文章管理信息。
     *
     * @param rows     每页显示的文章数量。
     * @param pageNum  当前的页码。
     * @return         返回文章管理的JSON响应。
     */
    @PostMapping(value = "/getArticleManagement")
    public String getArticleManagement(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {
        try {
            // 从文章服务中获取文章管理信息
            DataMap data = articleService.getArticleManagement(rows, pageNum);
            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("ArticleIdController getArticleManagement Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


//获取草稿文章！！或者修改文章的内容！！
    @GetMapping(value = "/getDraftArticle")
    public String getDraftArticle(HttpServletRequest request) {
        try {
            String id =(String) request.getSession().getAttribute("id");
            //判断文章是否是修改
            if (id != null) {
                Article article = articleService.getArticleByid(id);
                String tags = article.getArticleTags();
                int lastIndexOf = tags.lastIndexOf(",");
                String[] tagStr;

                // Check if comma exists in the tags string
                if (lastIndexOf != -1) {
                    tagStr = StringAndArray.stringToArray(tags.substring(0, lastIndexOf));
                } else {
                    // If no comma, use the entire tags string as an array
                    tagStr = new String[]{tags};
                }

                DataMap dataMap = articleService.getDraftArticle(article, tagStr, tagService.getTagsSizeByName(tagStr[0]));
                return JsonResult.build(dataMap).toJSON();
            }


            //判断是否写文章登录超时
            if (request.getSession().getAttribute("article")!=null){
                Article article =(Article) request.getSession().getAttribute("article");
                String[] tagStr = (String[]) request.getSession().getAttribute("articleTags");
             String articleGrade =  (String) request.getSession().getAttribute("articleGrade");
                if (!StringUtil.BLANK.equals(articleGrade) && articleGrade !=null) {
                    DataMap dataMap = articleService.getDraftArticle(article, tagStr, Integer.parseInt(articleGrade));
                    request.getSession().removeAttribute("article");
                    request.getSession().removeAttribute("articleTags");
                    request.getSession().removeAttribute("articleGrade");
                    return JsonResult.build(dataMap).toJSON();
                }

            }

            // 构建并返回成功的JSON响应
           return  JsonResult.fail().toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("ArticleIdController getDraftArticle Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 根据文章ID删除文章。
     *
     * @param id       要删除的文章的ID。
     * @return         返回删除操作的JSON响应。
     */
    @GetMapping(value = "/deleteArticle")
    public String deleteArticle(@RequestParam(value = "id") String id) {
        try {
            // 检查文章ID是否为空或无效
            if (StringUtil.BLANK.equals(id) || id == null) {
                return JsonResult.fail(CodeType.DELETE_ARTICLE_FAIL).toJSON();
            }
            // 从文章服务中删除指定ID的文章
            DataMap data = articleService.deleteArticle(id);
            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("ArticleIdController deleteArticle Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 发布新文章或修改现有文章。
     *
     * @param article          文章实体对象。
     * @param articleGrade     文章的等级。
     * @param principal        当前登录的用户。
     * @param request          HTTP请求对象，用于获取请求参数。
     * @return                 返回发布或修改操作的JSON响应。
     */
    @PostMapping(value = "/publishArticle")
    public String publishArticle(Article article, @RequestParam(value = "articleGrade") String articleGrade,
                                 @AuthenticationPrincipal Principal principal, HttpServletRequest request) {
        try {
            // 获取文章作者的名称
            //String name = "卡卡罗特";
            Principal userPrincipal = request.getUserPrincipal();
            String name = userPrincipal.getName();
            // 从请求中获取文章的HTML内容，并生成文章摘要
            BuildArticleTabloidUtil buildArticleTabloidUtil = new BuildArticleTabloidUtil();
            String articleHtmlContent = buildArticleTabloidUtil.buildArticleTabloid(request.getParameter("articleHtmlContent"));
            article.setArticleTabloid(articleHtmlContent + "...");

            // 处理前端传递的文章标签
            String[] articleTags = request.getParameterValues("articleTagsValue");

            String[] newarticleTags = new String[articleTags.length + 1];
            for (int i = 0; i < articleTags.length; i++) {
                // 清除特殊字符和空格
                newarticleTags[i] = articleTags[i].replaceAll("<br> ", StringUtil.BLANK).replaceAll("$nbsp", StringUtil.BLANK)
                        .replaceAll("\\s", StringUtil.BLANK).trim();
            }
            newarticleTags[articleTags.length] = article.getArticleType();

            // 将新标签插入到标签服务中
            tagService.insertTags(newarticleTags, Integer.parseInt(articleGrade));
            //为什么要把articleGrade转换成整数！！！TODO

            // 检查是否提供了文章ID，以确定是发布新文章还是修改现有文章
            String id = request.getParameter("id");
            if (!StringUtil.BLANK.equals(id) && id != null) {
                // 修改文章逻辑
                TimeUtil timeUtil = new TimeUtil();
                String updateDate = timeUtil.getFormatDateForThree();
                article.setArticleTags(StringAndArray.arrayToString( newarticleTags));//将数组专程string
                article.setUpdateDate(updateDate);
                article.setId(Integer.parseInt(id) );
                article.setAuthor(name);
               DataMap data= articleService.updateAricleById(article);
               return  JsonResult.build(data).toJSON();




            }

            // 设置文章的发布和更新日期
            TimeUtil timeUtil = new TimeUtil();
            String formatDateForThree = timeUtil.getFormatDateForThree();
            long articleId = timeUtil.getLongTime();

            // 设置文章的其他属性
            article.setArticleId(articleId);
            article.setAuthor(name);
            article.setArticleTags("String"); // TODO
            article.setPublishDate(formatDateForThree);
            article.setUpdateDate(formatDateForThree);

            // 将文章插入到文章服务中
            DataMap data = articleService.insertArticle(article);

            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("CategoriesController getArticleCategories Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    //验证当前登录用户是否有编辑或者发布文章权限！！
    @GetMapping(value = "/canYouWrite")
    public String YouWrite(@AuthenticationPrincipal Principal principal, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (!Objects.isNull(userPrincipal)){
                String username = userPrincipal.getName();
                boolean b =  userService.userNameIsExist(username);
                if (b){//如果b为true则是当前系统用户，可以编写文章！
                    return  JsonResult.success().toJSON();
                }
            }
            // 构建并返回成功的JSON响应
            return JsonResult.fail().toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("ArticleIdController canYouWrite Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
    //获取项目首页文章分页列表接口！！！
    @PostMapping(value = "/myArticles")
    public String getMyArticles(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {
        try {
            // 从文章服务中获取文章管理信息
            DataMap data = articleService.getMyArticles(rows,pageNum);
            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("ArticleIdController getMyArticles Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}
