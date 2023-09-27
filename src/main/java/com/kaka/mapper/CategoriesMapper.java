package com.kaka.mapper;

import com.kaka.model.Categories;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

// 使用@Mapper注解，标记这是一个MyBatis的Mapper接口
@Mapper
public interface CategoriesMapper {

    // 定义一个方法，用于从数据库中获取所有文章的分类
    List<Categories> getArticleCategories();

    // 定义一个方法，用于检查给定的分类名称在数据库中是否存在
    // 返回值是该分类名称对应的ID，如果不存在则返回0
    int findIsExistByCategoryName(String categoryNum);


    // 定义一个方法，用于在数据库中保存一个新的文章分类
    void saveCategories(Categories categories);

    // 定义一个方法，用于从数据库中删除指定名称的文章分类
    void deteleCategory(String categoryNum);

    // 定义一个方法，用于从数据库中获取所有文章分类的名称
    List<String> findCategoriesNames();
}
