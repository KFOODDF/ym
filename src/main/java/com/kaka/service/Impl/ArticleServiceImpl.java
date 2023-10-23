package com.kaka.service.Impl;

// 导入相关的类和包
import com.github.pagehelper.PageHelper;  // 分页插件
import com.github.pagehelper.PageInfo;  // 分页信息类
import com.kaka.mapper.ArticleMapper;  // 文章的数据访问层接口
import com.kaka.mapper.VisitorMapper;
import com.kaka.model.Article;  // 文章的数据模型
import com.kaka.model.Visitor;
import com.kaka.service.ArticleService;  // 文章的服务接口
import com.kaka.utils.DataMap;  // 数据映射工具类
import com.kaka.utils.StringAndArray;  // 字符串和数组处理工具类
import com.kaka.utils.StringUtil;  // 字符串处理工具类
import net.sf.json.JSONArray;  // JSON数组处理类
import net.sf.json.JSONObject;  // JSON对象处理类
import org.springframework.beans.factory.annotation.Autowired;  // Spring的自动注入注解
import org.springframework.stereotype.Service;  // 表示这是一个Spring服务类的注解
import org.springframework.transaction.annotation.Transactional;  // Spring的事务管理注解

import java.util.ArrayList;  // ArrayList集合类
import java.util.HashMap;  // HashMap集合类
import java.util.List;  // List集合接口
import java.util.Map;  // Map集合接口

// 使用@Service注解，表示这是一个Spring服务类
@Service
public class ArticleServiceImpl implements ArticleService {

    // 使用@Autowired注解，Spring会自动注入ArticleMapper对象
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private  VisitorMapper visitorMapper;
    // 插入文章的实现方法
    @Override
    public DataMap insertArticle(Article article) {
        // 如果原作者字段为空，则将作者字段的值赋给原作者字段
        if(StringUtil.BLANK.equals(article.getOriginalAuthor())){
            article.setOriginalAuthor(article.getAuthor());
        }
        // 如果文章URL为空，则拼接一个URL
        if (StringUtil.BLANK.equals(article.getArticleUrl())){
            article.setArticleUrl("www.javatiaozao.com" + "/article/" + article.getArticleId());
        }
        // TODO: 设置上一篇文章id

        // 调用Mapper层的saveArticle方法，将文章信息保存到数据库中
        articleMapper.saveArticle(article);

        // 为前端响应创建一个数据映射
        HashMap<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("articleTitle", article.getArticleTitle());
        dataMap.put("updatedate", article.getUpdateDate());
        dataMap.put("articleUrl", "/article/" + article.getArticleUrl());
        dataMap.put("author", article.getAuthor());
        // 返回成功的DataMap，并将数据封装进去
        return DataMap.success().setData(dataMap);
    }

    // 获取文章管理信息的实现方法
    @Override
    public DataMap getArticleManagement(int rows, int pageNum) {
        // 使用分页插件进行分页
        PageHelper.startPage(pageNum, rows);

        // 查询文章列表
        List<Article> articleList = articleMapper.getArticleManagement();
        PageInfo<Article> pageInfo = new PageInfo<>(articleList);

        // 创建JSON数组和对象来处理返回的数据
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
            String pageName = "article/" + article.getArticleId();
            articleJson.put("visitorNum",getVisitorNum(pageName)); // TODO: 获取真实的文章浏览量
            returnJsonArray.add(articleJson);
        }
        returnJsonObject.put("result", returnJsonArray);

        // 封装分页信息到JSON对象中
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

        //查询文章浏览量
    private Long getVisitorNum(String pageName) {
        Visitor visitorNumByPageName = visitorMapper.getVisitorNumByPageName(pageName);
        if (visitorNumByPageName !=null){
            return  visitorNumByPageName.getVisitorNum();
        }

        return 0l;
    }

    // 删除文章的实现方法
    @Override
    @Transactional  // 使用事务管理
    public DataMap deleteArticle(String id) {
        // 根据id查询文章信息
        Article article = articleMapper.getArticleById(id);

        // 更新文章的上一篇和下一篇的id
        articleMapper.updateLastNextId("lastArticleId", article.getArticleId(), article.getNextArticleId());
        articleMapper.updateLastNextId("lastArticleId", article.getArticleId(), article.getNextArticleId());

        // 删除本篇文章
        articleMapper.deleteArticle(article.getArticleId());

        // TODO: 删除文章相关的其他信息，如点赞、评论等

        // 返回成功的DataMap
        return DataMap.success();
    }

    // 根据id获取文章的实现方法
    @Override
    public Article getArticleByid(String id) {
        return  articleMapper.getArticleById(id);
    }

    // 获取草稿文章的实现方法
    @Override
    public DataMap getDraftArticle(Article article, String[] tagStr, Integer tagsSizeByName) {
        HashMap<String,Object> dataMap =new HashMap<>();
        dataMap.put("id",article.getId());
        dataMap.put("articleTitle",article.getArticleTitle());
        dataMap.put("articleContent",article.getArticleContent());
        dataMap.put("articleGrade",article.getId());
        dataMap.put("articleUrl",article.getId());
        dataMap.put("originalAuthor",article.getId());
        dataMap.put("articleTags",article.getId());
        dataMap.put("articleCategories",article.getId());
        return DataMap.success().setData(dataMap);
    }

    // 根据id更新文章的实现方法
    @Override
    public DataMap updateAricleById(Article article) {
        Article a = articleMapper.getArticleByIntId(article.getId());
        if ("原创".equals(article.getArticleType())){
            article.setOriginalAuthor(article.getOriginalAuthor());
            String url = "www.biying.com" + "/article" + a.getArticleId();
            article.setArticleUrl(url);
        }
        articleMapper.updateArticleById(article);
        HashMap<String,  Object> dataMap = new HashMap<>();
        dataMap.put("articleTitle",article.getArticleTitle());
        dataMap.put("updateDate",article.getUpdateDate());
        dataMap.put("articleUrl",article.getId());
        dataMap.put("author",article.getAuthor());
        return DataMap.success().setData(dataMap);
    }

    // 获取我的文章的实现方法
    @Override
    public DataMap getMyArticles(int rows, int pageNum) {
        // 使用分页插件进行分页
        PageHelper.startPage(pageNum,rows);
        // 查询文章列表
        List<Article> articleList = articleMapper.getArticleManagement();
        PageInfo<Article> articlePageInfo = new PageInfo<>(articleList);
        // 新建一个集合来封装文章数据
        ArrayList<Map<String,Object>> newArtilces = new ArrayList<>();
        // 遍历文章列表，将每篇文章的信息封装到Map中，然后添加到ArrayList中
        HashMap<String, Object> map;
        for (Article article : articleList){
            map  =  new HashMap<>();
            map.put("thisArticleUrl","/article/"+article.getArticleId());
            map.put("articleTitle","/article/"+article.getArticleTitle());
            map.put("articleType","/article/"+article.getArticleType());
            map.put("originalAuthor","/article/"+article.getOriginalAuthor());
            map.put("articleCategories","/article/"+article.getArticleCategories());
            map.put("publishDate","/article/"+article.getPublishDate());
            map.put("articleTabloid","/article/"+article.getArticleTabloid());
            map.put("likes","/article/"+article.getLikes());
            map.put("articleTags", StringAndArray.stringToArray(article.getArticleTags()));
            newArtilces.add(map);
        }
        JSONArray returnJsonArray = JSONArray.fromObject(newArtilces);
        HashMap<String, Object> pagInfoMap =new HashMap<>();
        pagInfoMap.put("pagNum", articlePageInfo.getPageNum());
        pagInfoMap.put("pagSize", articlePageInfo.getPageSize());
        pagInfoMap.put("pages", articlePageInfo.getPages());
        pagInfoMap.put("total", articlePageInfo.getTotal());
        pagInfoMap.put("isFirstPage", articlePageInfo.isIsFirstPage());
        pagInfoMap.put("isLastPage", articlePageInfo.isIsLastPage());
        returnJsonArray.add(pagInfoMap);
        return DataMap.success().setData(returnJsonArray);
    }

    @Override
    public Map<String, String> findArticleTitleByArticleId(long articleId) {
      Article articleInfo=  articleMapper.getArticleByAticleId(articleId);
    HashMap<String,String>articleMap= new HashMap<>();
    if (articleInfo != null){
        articleMap.put("articleTitle",articleInfo.getArticleTitle());
        articleMap.put("articleTabloid",articleInfo.getArticleTabloid());
    }



        return articleMap;
    }

    @Override
    public DataMap getArticleByArticleId(long articleId) {
        Article articleById = articleMapper.getArticleByAticleId(articleId);
        //当用户查看文章详情时候，我们在visitor表中插入一条记录
        visitorMapper.insertViistorArticlePage("article/" + articleId);
        return DataMap.success().setData(articleById);

    }
}
