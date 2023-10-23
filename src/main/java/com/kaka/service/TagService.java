package com.kaka.service;

import com.kaka.model.Tags;
import org.springframework.stereotype.Service;

/**
 * 标签服务接口，提供与标签相关的业务逻辑操作。
 */
@Service
public interface TagService {

    /**
     * 插入标签到数据库或更新现有标签的信息。
     *
     * @param newarticleTags 一个包含标签名称的字符串数组。
     * @param parseInt       文章的等级或分类，用整数表示。
     */
    void insertTags(String[] newarticleTags, int parseInt);

    /**
     * 根据标签名称查询其对应的大小或数量。
     *
     * @param s 要查询的标签的名称。
     * @return 返回与指定标签名称匹配的标签的大小或数量。
     */
    Integer getTagsSizeByName(String s);


}
