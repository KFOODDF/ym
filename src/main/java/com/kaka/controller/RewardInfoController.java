package com.kaka.controller;

import com.kaka.constant.CodeType;
import com.kaka.model.Reward;
import com.kaka.service.RewardInfoService;
import com.kaka.utils.DataMap;

import com.kaka.utils.JsonResult;
import com.kaka.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class RewardInfoController {
    @Autowired
    private RewardInfoService rewardInfoService;
    @PostMapping(value = "/getRewardInfo")
    public String getRewardInfo() {
        try {
            // 从点赞服务中标记所有点赞消息为已读
            DataMap data = rewardInfoService.getRewardInfo();
            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("RewardInfoController getRewardInfo Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    @PostMapping(value = "/addReward")
    public String addReward(@RequestParam("file") MultipartFile file, HttpServletRequest request, Reward reward) {
        try {

          DataMap data=  rewardInfoService.saveReward(reward,request,file);


            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("RewardInfoController addReward Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
        //删除募捐记录
    @GetMapping(value = "/deleteReward")
    public String deleteReward(Reward reward) {
        try {
            // 从点赞服务中标记所有点赞消息为已读
            DataMap data = rewardInfoService.deleteReward(reward);
            // 构建并返回成功的JSON响应
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            // 记录异常信息
            log.error("RewardInfoController deleteReward Exception", e);
        }
        // 如果发生错误，返回服务器异常的JSON响应
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}
