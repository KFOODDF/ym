package com.kaka.mapper;

import com.kaka.model.Tags;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签映射器接口，用于定义与标签相关的数据库操作。
 * 使用MyBatis框架进行数据库操作。
 */
@Mapper  // MyBatis的Mapper注解，标识这是一个映射器接口
public interface TagMapper {

    /**
     * 保存标签到数据库。
     *
     * @param tag 要保存的标签对象。
     */
    void saveTags(Tags tag);

    /**
     * 根据标签名称检查标签是否已存在于数据库中。
     *
     * @param newArticleTag 要检查的标签名称。
     * @return 如果标签存在则返回true，否则返回false。
     */
    boolean findIsExistByTagName(String newArticleTag);

    Integer getTagsSizeByName(String tagName);

}
