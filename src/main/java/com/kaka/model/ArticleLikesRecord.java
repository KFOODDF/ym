package com.kaka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleLikesRecord {
    private int id;

    private long articleId;

    private int likerId;

    private String likeDate;

    private boolean isRead;
}