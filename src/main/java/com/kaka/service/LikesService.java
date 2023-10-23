// 定义了一个名为`com.kaka.service`的包。在Java中，包用于组织和管理类和接口，以避免命名冲突并提高可维护性。
package com.kaka.service;

// 导入Friendlink模型类，该类可能定义了与“友情链接”相关的属性和方法。
import com.kaka.model.ArticleLikesRecord;
import com.kaka.model.Friendlink;

// 导入DataMap工具类，该类可能提供了数据映射或转换功能，例如将数据转换为特定格式或结构。
import com.kaka.utils.DataMap;

// 导入Spring框架的@Service注解。此注解用于标记一个类为Spring的服务组件，使其可以被Spring容器管理和自动注入。
import org.springframework.stereotype.Service;

// 使用@Service注解标记此接口，告诉Spring这是一个服务接口，需要进行特定的处理。
@Service
// 定义一个名为LikesService的接口，该接口可能包含与点赞功能相关的方法。
public interface LikesService {

    /**
     * 获取文章的点赞信息。
     *
     * @param rows 指定每页要显示的点赞数量。
     * @param pageNum 指定当前的页码。
     * @return 返回一个DataMap对象，该对象可能包含点赞操作的状态、消息或其他与点赞操作相关的数据。
     */
    DataMap getArticleThumbsUp(int rows, int pageNum);

    // 定义一个方法，用于读取所有的点赞信息。
    DataMap readAllThumbsUp();

    // 定义一个方法，用于获取友情链接信息。
    DataMap getFriendLink();

    // 定义一个方法，用于添加新的友情链接。
    DataMap addFriendLink(Friendlink friendlink);

    // 定义一个方法，用于更新现有的友情链接。
    DataMap updateFriendLink(Friendlink friendlink);

    // 定义一个方法，用于删除指定ID的友情链接。
    DataMap deleteFriendLink(String id);

    boolean isLiked(Long aLong, String likerId);

    DataMap updateLikeByArticleId(String articleId);


    void inertArticleLikesRecord(ArticleLikesRecord articleLikesRecord);
}
