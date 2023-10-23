package com.kaka.service;

import com.kaka.model.Reward;
import com.kaka.utils.DataMap;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Service
public interface RewardInfoService {
    DataMap getRewardInfo();

    DataMap saveReward(Reward reward, HttpServletRequest request, MultipartFile file);

    DataMap deleteReward(Reward reward);

}
