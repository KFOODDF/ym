package com.kaka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签模型类，用于表示文章的标签及其相关属性。
 * 使用Lombok库为类自动生成getter、setter、构造函数等方法。
 */
@Data
@AllArgsConstructor  // 自动生成全参数构造函数
@NoArgsConstructor   // 自动生成无参数构造函数
public class Tags {

    /**
     * 数据库中的标签ID（通常为自增ID）。
     */
    private int id;

    /**
     * 标签的名称。
     */
    private String tagName;

    /**
     * 标签的大小或权重，可能用于表示标签的受欢迎程度或文章数量。
     */
    private int tagSize;

    /**
     * 构造函数，用于创建一个新的标签对象。
     *
     * @param tagName 标签的名称。
     * @param tagSize 标签的大小或权重。
     */
    public Tags(String tagName, int tagSize) {
        this.tagName = tagName;
        this.tagSize = tagSize;
    }
}
