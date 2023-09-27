package com.kaka.service.Impl;

import com.kaka.mapper.TagMapper;
import com.kaka.model.Tags;
import com.kaka.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    // 使用@Autowired注解，Spring会自动注入TagMapper对象
    @Autowired
    private TagMapper tagMapper;

    /**
     * 插入文章的标签
     *
     * @param newArticleTags 新文章的标签数组
     * @param tagSize 标签的大小
     */
    @Override
    public void insertTags(String[] newArticleTags, int tagSize) {
        // 遍历每一个新文章的标签
        for (String newArticleTag : newArticleTags) {
            // 在插入标签之前，需要判断数据库中是否已经存在该标签
            // 如果标签已经存在，则不进行插入操作
            // 如果标签不存在，则进行插入操作
            if (!tagMapper.findIsExistByTagName(newArticleTag)) {
                // 创建一个新的标签对象
                Tags tag = new Tags(newArticleTag, tagSize);
                // 调用Mapper层的saveTags方法，将标签信息保存到数据库中
                tagMapper.saveTags(tag);
            }
        }
    }
}
