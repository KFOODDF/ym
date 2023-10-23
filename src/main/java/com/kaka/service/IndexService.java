package com.kaka.service;

import com.kaka.utils.DataMap;
import org.springframework.stereotype.Service;
//首页实现接口
@Service
public interface IndexService {
    DataMap getSiteInfo();


    DataMap getNewComment(int rows, int pageNum);

    DataMap getNewLeaveWord(int rows, int pageNum);

    DataMap findTagsCloud();




}
