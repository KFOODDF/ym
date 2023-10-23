package com.kaka.mapper;

import com.kaka.model.Reward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RewardInfoMapper {
    List<Reward> getRewardInfo();

    void saveReward(Reward reward);

    int deleteReward(int id);

}
