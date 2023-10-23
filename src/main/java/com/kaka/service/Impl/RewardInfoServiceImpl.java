package com.kaka.service.Impl;

import com.kaka.constant.CodeType;
import com.kaka.mapper.RewardInfoMapper;
import com.kaka.model.Reward;
import com.kaka.service.RewardInfoService;
import com.kaka.utils.DataMap;
import com.kaka.utils.FileUtil;
import com.kaka.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//募捐管理实现层
@Service
public class RewardInfoServiceImpl implements RewardInfoService {
    @Autowired
    private RewardInfoMapper rewardInfoMapper;

    @Override
    public DataMap getRewardInfo() {
        List<Reward> rewardList = rewardInfoMapper.getRewardInfo();
        return DataMap.success().setData(rewardList);
    }

    @Override
    public DataMap saveReward(Reward reward, HttpServletRequest request, MultipartFile file) {
        //获取募捐时间
        String rewardDate = request.getParameter("reward-date");
        //上传附件
        FileUtil fileUtil = new FileUtil();
        String filePath =  this.getClass().getResource("/").getPath().substring(1) + "blogImg/";
        String contentType = file.getContentType();
        String fileEx = contentType.substring(contentType.indexOf("/") + 1);
        TimeUtil timeUtil = new TimeUtil();
        String fileName = timeUtil.getLongTime()+ "."+fileEx;
        String fileCatalog ="rewardRecord" + timeUtil.getFormatDateForThree();
        String fileUrl =fileUtil.uploadFile(fileUtil.multipartFileToFile(file,filePath,fileName),fileCatalog);
        //处理募捐记录
        reward.setRewardDate(timeUtil.stringToDateThree(rewardDate));
        reward.setRewardUrl(fileUrl);
        reward.setFundRaiser("卡卡罗特");
        reward.setFundraisingPlace("1");
        reward.setFundRaisingSources("2");
        rewardInfoMapper.saveReward(reward);
        return DataMap.success(CodeType.ADD_REWARD_SUCCESS).setData(reward.getId());
    }

    @Override
    public DataMap deleteReward(Reward reward) {
        int result=rewardInfoMapper.deleteReward(reward.getId());
        return DataMap.success(CodeType.DELETE_REWARD_SUCCESS).setData(result);
    }

}
