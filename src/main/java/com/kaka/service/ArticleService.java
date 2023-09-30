package com.kaka.service;

import com.kaka.model.Article;
import com.kaka.utils.DataMap;
import org.springframework.stereotype.Service;

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



    Article getArticleByid(String id);

    DataMap getDraftArticle(Article article, String[] tagStr, Integer tagsSizeByName);


    DataMap updateAricleById(Article article);

    DataMap getMyArticles(int rows, int pageNum);

}
