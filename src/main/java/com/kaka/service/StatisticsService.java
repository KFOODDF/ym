package com.kaka.service;

import com.kaka.utils.DataMap;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface StatisticsService {
    DataMap getVisitorNumByPageName(HttpServletRequest request, String pageName);

    DataMap getStatisticsInfo(HttpServletRequest request);

}
