package com.kaka.mapper;

import com.kaka.model.Visitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VisitorMapper {
    Visitor getVisitorNumByPageName(@Param("pageName") String pageName);


    long getTotalVisitor();


    void insertViistorArticlePage(String s);

    void updateVisitorBypageName(@Param("pageName")String pageName, @Param("visitorNum") String visitorNum);

}
