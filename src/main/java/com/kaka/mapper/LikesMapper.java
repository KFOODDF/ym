package com.kaka.mapper;

import com.kaka.model.ArticleLikesRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikesMapper {
    List<ArticleLikesRecord> getArticleThumbsUp();


   Integer getMagIsNotReadNum();


}
