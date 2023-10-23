package com.kaka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Privateword {
    private Integer id;

    private String privateWord;

    private String publisherId;

    private String replierId;

    private String replyContent;

    private String publisherDate;
}