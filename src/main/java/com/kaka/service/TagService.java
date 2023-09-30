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

   Integer getTagsSizeByName(String s);

}
