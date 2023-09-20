package com.kaka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//下边的注解是加入了lombok的依赖，他们可以生成get，set的方法  也可以生成无参构造和有参构造
@NoArgsConstructor
@AllArgsConstructor
@Data
public class demo {
    private int id;
    private String name;
    private int age;
    private String birthday;

}
