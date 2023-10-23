// 定义当前类所在的包
package com.kaka.service.Impl;

// 导入相关的类和接口
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaka.mapper.FeedBackMapper;
import com.kaka.mapper.UserMapper;
import com.kaka.model.Feedback;
import com.kaka.model.Privateword;
import com.kaka.service.FeedBackService;
import com.kaka.service.UserService;
import com.kaka.utils.DataMap;
import com.kaka.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 使用@Service注解标记这是一个Spring服务类
@Service
// 定义FeedBackServiceImpl类，它实现了FeedBackService接口
public class FeedBackServiceImpl implements FeedBackService {

    // 使用@Autowired注解自动注入FeedBackMapper的实例
    @Autowired
    FeedBackMapper feedbackMapper;

    // 使用@Autowired注解自动注入UserService的实例
    @Autowired
    UserService userService;

    // 重写FeedBackService接口中的getAllFeedback方法
    @Override
    public DataMap getAllFeedback(int rows, int pageNum) {
        // 使用PageHelper插件进行分页查询
        PageHelper.startPage(pageNum, rows);

        // 从数据库中获取所有的反馈信息
        List<Feedback> feedbackList = feedbackMapper.getAllFeedback();
        // 使用PageInfo对查询结果进行包装
        PageInfo<Feedback> pageInfo = new PageInfo<>(feedbackList);

        // 创建JSON数组和对象，用于构建返回的数据结构
        JSONArray returnJsonArray = new JSONArray();
        JSONObject returnJsonObject = new JSONObject();

        // 遍历反馈列表，将每个反馈信息转换为JSON对象，并添加到JSON数组中
        JSONObject feedbackJson;
        for (Feedback feedBack : feedbackList) {
            feedbackJson = new JSONObject();
            feedbackJson.put("person", userService.findUsernameByid(feedBack.getPersonId()));
            feedbackJson.put("feedbackDate", feedBack.getFeedbackDate());
            feedbackJson.put("feedbackContent", feedBack.getFeedbackContent());
            if (feedBack.getContactInfo() == null) {
                feedbackJson.put("contactInfo", StringUtil.BLANK);
            } else {
                feedbackJson.put("contactInfo", feedBack.getContactInfo());
            }
            returnJsonArray.add(feedbackJson);
        }
        returnJsonObject.put("result", returnJsonArray);

        // 将分页信息添加到返回的JSON对象中
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum", pageInfo.getPageNum());
        pageJson.put("pageSize", pageInfo.getSize());
        pageJson.put("total", pageInfo.getTotal());
        pageJson.put("pages", pageInfo.getPages());
        pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
        pageJson.put("isLastPage", pageInfo.isIsLastPage());
        returnJsonObject.put("pageInfo", pageJson);

        // 返回成功的DataMap，并将数据封装进去
        return DataMap.success().setData(returnJsonObject);
    }

    // 重写FeedBackService接口中的getAllPrivateWord方法
    @Override
    public DataMap getAllPrivateWord() {
        // 从数据库中获取所有的私人消息
        List<Privateword> privatewords = feedbackMapper.getAllPrivateWord();

        // 创建JSON数组和对象，用于构建返回的数据结构
        JSONObject returnJson = new JSONObject();
        JSONArray returnJsonArray = new JSONArray();

        // 遍历私人消息列表，将每个消息转换为JSON对象，并添加到JSON数组中
        for (Privateword privateword : privatewords) {
            JSONObject userJson = new JSONObject();
            userJson.put("privateWord", privateword.getPrivateWord());
            userJson.put("publisher", userService.findUsernameByid(Integer.parseInt(privateword.getPublisherId())));
            userJson.put("publisherDate", privateword.getPublisherDate());
            userJson.put("id", privateword.getId());
            if (privateword.getReplyContent() == null) {
                userJson.put("replyContent", StringUtil.BLANK);
            } else {
                userJson.put("replyContent", privateword.getReplyContent());
            }
            returnJsonArray.add(userJson);
        }

        // 将JSON数组添加到返回的JSON对象中
        returnJson.put("result", returnJsonArray);

        // 返回成功的DataMap，并将数据封装进去
        return DataMap.success().setData(returnJson);
    }
}
