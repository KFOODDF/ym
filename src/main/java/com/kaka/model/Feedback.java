package com.kaka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    private Integer id;

    private String feedbackContent;

    private String contactInfo;

    private Integer personId;

    private String feedbackDate;
}