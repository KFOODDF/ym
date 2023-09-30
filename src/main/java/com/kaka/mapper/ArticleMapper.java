package com.kaka.mapper;

import com.kaka.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MyBatis 映射器接口，用于定义与文章相关的数据库操作。
 * 该接口定义了一系列方法，用于在数据库中保存、查询和删除文章。
 */
@Mapper
public interface ArticleMapper {

    /**
     * 将文章对象保存到数据库中。
     *
     * @param article 要保存的文章对象。
     */
    void saveArticle(Article article);

    /**
     * 从数据库中获取所有文章的管理信息。
     *
     * @return 返回一个包含所有文章的列表。
     */
    List<Article> getArticleManagement();

    /**
     * 根据文章ID从数据库中获取特定的文章。
     *
     * @param id 要查询的文章的ID。
     * @return 返回与指定ID匹配的文章对象。
     */
    Article getArticleById(String id);

    /**
     * 更新文章的上一篇或下一篇文章的ID。
     *
     * @param lastOrNextstr 指定是更新上一篇还是下一篇文章的ID（例如："last" 或 "next"）。
     * @param updateId      要更新的文章的ID。
     * @param articleId     当前文章的ID。
     */
    void updateLastNextId(@Param("lastOrNextstr") String lastOrNextstr, @Param("updateId") Long updateId, @Param("articleId") Long articleId);

    /**
     * 根据文章ID从数据库中删除特定的文章。
     *
     * @param articleId 要删除的文章的ID。
     */
    void deleteArticle(Long articleId);

    Article getArticleByIntId(Integer id);

    void updateArticleById(Article article);

    String getArticleTitleByArtitleId(long articleId);

    String getArticleAuthorByArtitleId(long articleId);

}
