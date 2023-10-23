package com.kaka.mapper;

import com.kaka.model.CommentRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentRecordMapper {




    Integer countCommentMapper();

    List<CommentRecord> queryComment();

}
