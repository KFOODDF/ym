package com.kaka.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaka.mapper.ArticleMapper;
import com.kaka.model.Article;
import com.kaka.service.ArticleService;
import com.kaka.utils.DataMap;
import com.kaka.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    // 通过@Autowired注解，Spring会自动注入ArticleMapper对象
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public DataMap insertArticle(Article article) {
        // 处理一下剩余非空字段
        // 如果原作者字段为空，则将作者字段的值赋给原作者字段
        if(StringUtil.BLANK.equals(article.getOriginalAuthor())){
            article.setOriginalAuthor(article.getAuthor());
        }
        // 如果文章URL为空，则拼接一个URL
        if (StringUtil.BLANK.equals(article.getArticleUrl())){
            // 拼接一个url，如果是生产环境：www.javatiaozao.com
            article.setArticleUrl("www.javatiaozao.com" + "/article/" + article.getArticleId());
        }
        // TODO 设置上一篇文章id

        // 调用Mapper层的saveArticle方法，将文章信息保存到数据库中
        articleMapper.saveArticle(article);

        // 给前端响应，封装数据！
        HashMap<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("articleTitle", article.getArticleTitle());
        dataMap.put("updatedate", article.getUpdateDate());
        dataMap.put("articleUrl", "/article/" + article.getArticleUrl());
        dataMap.put("author", article.getAuthor());
        // 返回成功的DataMap，并将数据封装进去
        return DataMap.success().setData(dataMap);
    }

    @Override
    public DataMap getArticleManagement(int rows, int pageNum) {
        // 开启分页插件功能
        PageHelper.startPage(pageNum, rows);

        // 查询文章集合，并且存入集合中
        List<Article> articleList = articleMapper.getArticleManagement();
        PageInfo<Article> pageInfo = new PageInfo<>(articleList);

        // 返回数据处理
        JSONArray returnJsonArray = new JSONArray();
        JSONObject returnJsonObject = new JSONObject();

        // 遍历文章列表，将每篇文章的信息封装到JSONObject中，然后添加到JSONArray中
        for (Article article : articleList) {
            JSONObject articleJson = new JSONObject();
            articleJson.put("id", article.getId());
            articleJson.put("articleId", article.getArticleId());
            articleJson.put("originalAuthor", article.getOriginalAuthor());
            articleJson.put("articleTitle", article.getArticleTitle());
            articleJson.put("publishDate", article.getPublishDate());
            articleJson.put("articleCategories", article.getArticleCategories());
            articleJson.put("visitorNum", "999"); // TODO 文章浏览量
            returnJsonArray.add(articleJson);
        }
        returnJsonObject.put("result", returnJsonArray);

        // 封装分页信息
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum", pageInfo.getPageNum());
        pageJson.put("pageSize", pageInfo.getSize());
        pageJson.put("total", pageInfo.getTotal());
        pageJson.put("pages", pageInfo.getPages());
        pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
        pageJson.put("isLastPage", pageInfo.isIsLastPage());
        returnJsonObject.put("pageInfo", pageJson);

        // 返回成功的DataMap，并将数据封装进去
        return DataMap.success().setData(returnJsonObject);
    }

    @Override
    @Transactional
    public DataMap deleteArticle(String id) {
        // 需要处理文章相关联的所有东西
        // 根据id查询文章信息
        Article article = articleMapper.getArticleById(id);

        // 逻辑删除：就是实际没有删除 只是状态删除了 数据库还有
        // 物理删除：直接在数据库删除！
        articleMapper.updateLastNextId("lastArticleId", article.getArticleId(), article.getNextArticleId());
        articleMapper.updateLastNextId("lastArticleId", article.getArticleId(), article.getNextArticleId());

        // 删除本篇文章
        articleMapper.deleteArticle(article.getArticleId());

        // 文章对应的点赞记录、评论、喜欢的纪录也要删除！
        // TODO

        // 返回成功的DataMap
        return DataMap.success();
    }
}

