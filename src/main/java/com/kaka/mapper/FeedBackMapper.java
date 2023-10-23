package com.kaka.mapper;

import com.kaka.model.Feedback;
import com.kaka.model.Privateword;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedBackMapper {
    List <Feedback> getAllFeedback();


    List<Privateword> getAllPrivateWord();

}
