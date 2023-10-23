package com.kaka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveMessageLikesRecord {
    private Integer id;

    private String pageName;

    private Integer pId=0;

    private Integer likerId;

    private String likeDate;
}