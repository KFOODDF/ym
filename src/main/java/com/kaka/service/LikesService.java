package com.kaka.service;

import com.kaka.utils.DataMap;
import org.springframework.stereotype.Service;

@Service
//点赞管理接口
public interface LikesService {

    DataMap getArticleThumbsUp(int rows, int pageNum);

}
