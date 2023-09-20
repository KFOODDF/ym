package com.kaka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private int id;
    private String phone;
    private String username;
    private String password;
    private String gender;
    private String trueName;
    private String birthday;
    private String email;
    private String personalBrief;
    private  String  avatarImgUrl;
    private  String recentlyLanded;
    private List<Role> roles;
}
