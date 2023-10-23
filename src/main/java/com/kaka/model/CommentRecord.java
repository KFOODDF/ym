package com.kaka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRecord {
    private Long id;

    private Long pId;

    private Long articleId;

    private Integer answererId;

    private Integer respondentId;

    private String commentDate;

    private Integer likes;

    private String commentContent;

    private Boolean isRead;
}