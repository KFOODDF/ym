package com.kaka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friendlink {
    private Integer id;

    private String blogger;

    private String url;

    public Friendlink(String blogger, String url) {
        this.blogger = blogger;
        this.url = url;

    }
}