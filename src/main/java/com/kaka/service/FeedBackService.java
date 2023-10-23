package com.kaka.service;

import com.kaka.utils.DataMap;
import org.springframework.stereotype.Service;

@Service
public interface FeedBackService {


    DataMap getAllFeedback(int rows, int pageNum);

    DataMap getAllPrivateWord();

}
