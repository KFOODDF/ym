package com.kaka.mapper;

import com.kaka.model.demo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface DemoMapper {
    @Select("select * from  demo")
    public demo[] findAll();
}
