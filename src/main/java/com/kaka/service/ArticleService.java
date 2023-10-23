package com.kaka.service;

import com.kaka.model.Article;  // 导入文章数据模型
import com.kaka.utils.DataMap;  // 导入数据映射工具类
import org.springframework.stereotype.Service;  // 导入Spring的服务注解

import java.util.Map;

// 使用@Service注解，表示这是一个Spring服务接口
@Service
public interface ArticleService {

    /**
     * 插入一篇新的文章。
     *
     * @param article 要插入的文章对象，包含文章的各种属性和内容。
     * @return DataMap 返回一个DataMap对象，该对象可能包含插入操作的状态、消息或其他与插入操作相关的数据。
     */
    DataMap insertArticle(Article article);

    /**
     * 获取文章管理界面的文章列表。
     *
     * @param rows 每页要显示的文章数量。
     * @param pageNum 当前的页码。
     * @return DataMap 返回一个DataMap对象，该对象包含分页后的文章列表、当前页码、总页数等与分页相关的数据。
     */
    DataMap getArticleManagement(int rows, int pageNum);

    /**
     * 根据文章ID删除一篇文章。
     *
     * @param id 要删除的文章的ID。
     * @return DataMap 返回一个DataMap对象，该对象可能包含删除操作的状态、消息或其他与删除操作相关的数据。
     */
    DataMap deleteArticle(String id);

    /**
     * 根据文章ID获取文章。
     *
     * @param id 要查询的文章的ID。
     * @return Article 返回一个Article对象，包含文章的各种属性和内容。
     */
    Article getArticleByid(String id);

    /**
     * 获取草稿文章。
     *
     * @param article 草稿文章对象。
     * @param tagStr 文章标签字符串数组。
     * @param tagsSizeByName 标签大小。
     * @return DataMap 返回一个DataMap对象，该对象可能包含草稿文章的状态、消息或其他与草稿文章相关的数据。
     */
    DataMap getDraftArticle(Article article, String[] tagStr, Integer tagsSizeByName);

    /**
     * 根据文章ID更新文章。
     *
     * @param article 要更新的文章对象。
     * @return DataMap 返回一个DataMap对象，该对象可能包含更新操作的状态、消息或其他与更新操作相关的数据。
     */
    DataMap updateAricleById(Article article);

    /**
     * 获取我的文章列表。
     *
     * @param rows 每页要显示的文章数量。
     * @param pageNum 当前的页码。
     * @return DataMap 返回一个DataMap对象，该对象包含分页后的文章列表、当前页码、总页数等与分页相关的数据。
     */
    DataMap getMyArticles(int rows, int pageNum);

    Map<String, String> findArticleTitleByArticleId(long articleId);

    DataMap getArticleByArticleId(long articleId);

}
