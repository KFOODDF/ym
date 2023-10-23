package com.kaka.mapper;

// 导入点赞记录的数据模型
import com.kaka.model.ArticleLikesRecord;

// 导入友情链接的数据模型
import com.kaka.model.Friendlink;

// 导入MyBatis的映射器注解
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

// 导入Java的List接口，用于处理和返回对象列表
import java.util.List;

// 使用@Mapper注解标记这是一个MyBatis的映射器接口，告诉Spring Boot这是一个MyBatis的数据访问层接口
@Mapper
public interface LikesMapper {

    // 定义一个方法，用于从数据库中获取文章的点赞记录列表
    List<ArticleLikesRecord> getArticleThumbsUp();

    // 定义一个方法，用于从数据库中获取未读消息的数量
    Integer getMagIsNotReadNum();

    // 定义一个方法，用于将所有点赞消息标记为已读
    void readAllThumbsUp();

    // 定义一个方法，用于从数据库中获取所有的友情链接
    List<Friendlink> getFriendLink();

    // 定义一个方法，用于向数据库中添加一个新的友情链接
    void addFriendlink(Friendlink friendlink);

    // 定义一个方法，用于在数据库中更新一个已存在的友情链接
    void updateFriendLink(Friendlink friendlink);

    // 定义一个方法，用于从数据库中删除一个指定ID的友情链接
    void deleteFriendLink(String id);

    ArticleLikesRecord isLiked(@Param("articleId") Long articleId, @Param("likerId") int likerId);

    void inertArticleLikesRecord(ArticleLikesRecord articleLikesRecord);

}
