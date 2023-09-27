package com.kaka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章模型类，用于表示文章的各种属性。
 * 使用Lombok库为类自动生成getter、setter、构造函数等方法。
 */
@Data
@AllArgsConstructor  // 自动生成全参数构造函数
@NoArgsConstructor   // 自动生成无参数构造函数
public class Article {

    /**
     * 数据库中的文章ID（通常为自增ID）。
     */
    private int id;

    /**
     * 文章的唯一标识ID。
     */
    private Long articleId;

    /**
     * 文章的作者。
     */
    private String author;

    /**
     * 文章的原作者（例如，如果文章是转载的）。
     */
    private String originalAuthor;

    /**
     * 文章的标题。
     */
    private String articleTitle;

    /**
     * 文章的内容。
     */
    private String articleContent;

    /**
     * 文章的标签，用于分类和搜索。
     */
    private String articleTags;

    /**
     * 文章的类型（例如：技术、生活等）。
     */
    private String articleType;

    /**
     * 文章的分类。
     */
    private String articleCategories;

    /**
     * 文章的发布日期。
     */
    private String publishDate;

    /**
     * 文章的最后更新日期。
     */
    private String updateDate;

    /**
     * 文章的URL地址。
     */
    private String articleUrl;

    /**
     * 文章的摘要或简介。
     */
    private String articleTabloid;

    /**
     * 文章的点赞数量。
     */
    private int likes;

    /**
     * 上一篇文章的ID。
     */
    private Long lastArticleId;

    /**
     * 下一篇文章的ID。
     */
    private Long nextArticleId;
}
