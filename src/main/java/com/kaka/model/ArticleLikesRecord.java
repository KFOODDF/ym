package com.kaka.model;

// 导入Lombok库的注解，用于自动生成getter、setter、构造函数等
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 使用@Data注解，Lombok会为类的所有属性自动生成getter和setter方法
@Data
// 使用@AllArgsConstructor注解，Lombok会为类生成一个包含所有属性的构造函数
@AllArgsConstructor
// 使用@NoArgsConstructor注解，Lombok会为类生成一个无参数的构造函数
@NoArgsConstructor
public class ArticleLikesRecord {

    // 文章点赞记录的唯一标识ID
    private int id;

    // 被点赞的文章的ID
    private long articleId;

    // 点赞者的用户ID
    private int likerId;

    // 点赞的日期
    private String likeDate;

    // 标记点赞是否已读，true表示已读，false表示未读
    private boolean isRead;
    public ArticleLikesRecord(Long articleId, int likerId, String likeDate) {
        this.articleId = articleId;
        this.likerId = likerId;
        this.likeDate = likeDate;
    }
}
