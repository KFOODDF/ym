package com.kaka.mapper;

import com.kaka.model.Tags;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 标签映射器接口，用于定义与标签相关的数据库操作。
 * 使用MyBatis框架进行数据库操作。
 */
@Mapper  // MyBatis的Mapper注解，标识这是一个映射器接口
public interface TagMapper {

    /**
     * 将标签对象保存到数据库中。
     *
     * @param tag 要保存的标签对象。
     */
    void saveTags(Tags tag);

    /**
     * 根据提供的标签名称查询数据库，检查该标签是否已存在。
     *
     * @param newArticleTag 要查询的标签名称。
     * @return 如果数据库中已存在该标签，则返回true，否则返回false。
     */
    boolean findIsExistByTagName(String newArticleTag);

    /**
     * 根据标签名称查询其在数据库中的数量或大小。
     *
     * @param tagName 要查询的标签名称。
     * @return 返回与指定标签名称匹配的标签的数量或大小。
     */
    Integer getTagsSizeByName(String tagName);

    Integer countTagsNum();

    List<Tags> findTagsCloud();


}
