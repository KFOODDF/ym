package com.kaka.service;

import com.kaka.utils.DataMap;
import org.springframework.stereotype.Service;

// 使用@Service注解，标记这是一个Spring的服务接口
@Service
public interface CategoriesService {

    /**
     * 获取所有文章的分类信息。
     *
     * @return 返回包含所有文章分类信息的DataMap对象。
     */
    DataMap getArticleCategories();

    /**
     * 更新文章的分类信息。可以是新增或删除分类。
     *
     * @param categoryNum 分类名称或编号。
     * @param type 操作类型。例如：1表示新增，其他值表示删除。
     * @return 返回操作结果，成功或失败的DataMap对象。
     */
    DataMap updateCategory(String categoryNum, int type);

    /**
     * 获取所有文章分类的名称。
     *
     * @return 返回包含所有文章分类名称的DataMap对象。
     */
    DataMap findCategoriesNames();

    DataMap getCategoryArticle(String category, int rows, int pageNum);

    DataMap findCategoriesNameAndArticleNum();

    DataMap  getPageLeaveMessage(String pageName, String userName);

    void publishLeaveMessage(String leaveMessageContent, String userName, String pageName);

}
