package com.kaka.mapper;

import com.kaka.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MyBatis 映射器接口。这个接口定义了与文章相关的数据库操作。
 * 它提供了一系列的方法，用于在数据库中保存、查询和删除文章。
 */
@Mapper
public interface ArticleMapper {

    /**
     * 将文章对象保存到数据库。
     *
     * @param article 需要保存的文章对象。
     */
    void saveArticle(Article article);

    /**
     * 查询数据库中所有的文章管理信息。
     *
     * @return 返回一个包含所有文章的列表。
     */
    List<Article> getArticleManagement();

    /**
     * 根据文章的ID从数据库中查询特定的文章。
     *
     * @param id 需要查询的文章的ID。
     * @return 返回与给定ID匹配的文章对象。
     */
    Article getArticleById(String id);

    /**
     * 更新文章的上一篇或下一篇文章的ID。
     *
     * @param lastOrNextstr 指定是更新上一篇还是下一篇文章的ID（例如："last" 或 "next"）。
     * @param updateId      需要更新的文章的ID。
     * @param articleId     当前文章的ID。
     */
    void updateLastNextId(@Param("lastOrNextstr") String lastOrNextstr, @Param("updateId") Long updateId, @Param("articleId") Long articleId);

    /**
     * 根据文章的ID从数据库中删除特定的文章。
     *
     * @param articleId 需要删除的文章的ID。
     */
    void deleteArticle(Long articleId);

    /**
     * 根据整数类型的ID从数据库中查询特定的文章。
     *
     * @param id 需要查询的文章的整数ID。
     * @return 返回与给定整数ID匹配的文章对象。
     */
    Article getArticleByIntId(Integer id);

    /**
     * 根据文章的ID更新文章信息。
     *
     * @param article 需要更新的文章对象。
     */
    void updateArticleById(Article article);

    /**
     * 根据文章的ID从数据库中查询文章的标题。
     *
     * @param articleId 需要查询的文章的ID。
     * @return 返回与给定ID匹配的文章的标题。
     */
    String getArticleTitleByArtitleId(long articleId);

    /**
     * 根据文章的ID从数据库中查询文章的作者。
     *
     * @param articleId 需要查询的文章的ID。
     * @return 返回与给定ID匹配的文章的作者。
     */
    String getArticleAuthorByArtitleId(long articleId);

    Article getArticleByAticleId(long articleId);

    int countArticleNum();


    void updateLikeByArticleId(String articleId);

    int queryLiksByarticle(String articleId);

    String countArticleNumByCategor(String categoryName);

}
