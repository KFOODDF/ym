package com.kaka.service.Impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaka.constant.CodeType;
import com.kaka.mapper.ArticleMapper;
import com.kaka.mapper.CategoriesMapper;
import com.kaka.mapper.UserMapper;
import com.kaka.model.Article;
import com.kaka.model.Categories;
import com.kaka.model.LeaveMessageRecord;
import com.kaka.model.UserReadNews;
import com.kaka.service.CategoriesService;
import com.kaka.service.UserService;
import com.kaka.utils.DataMap;
import com.kaka.utils.StringAndArray;
import com.kaka.utils.StringUtil;
import com.kaka.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 使用@Service注解，标记这是一个Spring的服务类
@Service
public class CategoriesServiceImpl implements CategoriesService {

    // 使用@Autowired注解，自动注入CategoriesMapper接口的实现类
    @Autowired
   private CategoriesMapper categoriesMapper;
        @Autowired
    private  ArticleMapper articleMapper;
        @Autowired
   private UserService userService;
        @Autowired
        private UserMapper userMapper;
        @Autowired
        private  HashRedisServiceImpl hashRedisServiceImpl;


    // 实现接口中的getArticleCategories方法
    @Override
    public DataMap getArticleCategories() {
        // 从数据库中查询所有文章的分类
        List<Categories> cleCategories = categoriesMapper.getArticleCategories();

        // 初始化一个JSONObject对象，用于最终返回给前端的数据
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();


        // 遍历查询到的分类列表，将每个分类的信息封装到一个JSONObject中
        for (Categories category : cleCategories) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", category.getId());
            jsonObject.put("categoryName", category.getCategoryName());

            // 将封装好的JSONObject添加到JSONArray中
            jsonArray.add(jsonObject);
        }

        // 将JSONArray添加到最终的JSONObject中
        returnJson.put("result", jsonArray);

        // 返回成功的数据
        return DataMap.success().setData(returnJson);
    }

    // 实现接口中的updateCategory方法
    @Override
    public DataMap updateCategory(String categoryNum, int type) {
        // 从数据库中查询指定分类名称是否存在
        int isExistCategoryName = categoriesMapper.findIsExistByCategoryName(categoryNum);

        // 根据type参数判断是新增分类还是删除分类
        if (type == 1) {
            // type为1表示新增分类
            if (isExistCategoryName == 0) {
                // 如果分类不存在，则新增
                Categories categories = new Categories();
                categories.setCategoryName(categoryNum);
                categoriesMapper.saveCategories(categories);
                int newCategoryiesId = categoriesMapper.findIsExistByCategoryName(categoryNum);
                return DataMap.success(CodeType.ADD_CATEGORY_SUCCESS).setData(newCategoryiesId);
            }
        } else {
            // type不为1表示删除分类
            if (isExistCategoryName != 0) {
                // TODO: 考虑查询分类下是否存在文章
                // 如果分类存在，则删除
                categoriesMapper.deteleCategory(categoryNum);
                return DataMap.success(CodeType.DELETE_CATEGORY_SUCCESS);
            }
        }

        // 如果分类不存在，则返回失败的信息
        return DataMap.fail(CodeType.CATEGORY_NOT_EXIST);
    }

    // 实现接口中的findCategoriesNames方法
    @Override
    public DataMap findCategoriesNames() {
        // 从数据库中查询所有文章分类的名称
        List<String> categoryNames = categoriesMapper.findCategoriesNames();

        // 返回成功的数据
        return DataMap.success().setData(categoryNames);
    }

    @Override
    public DataMap getCategoryArticle(String category, int rows, int pageNum) {

        List<Article> categoryList;
        //开启分页
        PageHelper.startPage(pageNum,rows);
        //当category为空时  查询所有分类
        if(StringUtil.BLANK.equals(category)){
            categoryList  = categoriesMapper.getCategoryArticles();
            category = "全部分类";
        }else {

              categoryList=categoriesMapper.getCategoryArticle(category);
        }


        PageInfo<Article> articlePageInfo = new PageInfo<Article>(categoryList);
        //封装数据
        JSONArray articleJsonArray = new JSONArray();
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",articlePageInfo.getPageNum());
        pageJson.put("pages",articlePageInfo.getPages());
        pageJson.put("pageSize",articlePageInfo.getPageSize());
        pageJson.put("total",articlePageInfo.getTotal());
        pageJson.put("isFirstPage", articlePageInfo.isIsFirstPage());
        pageJson.put("isLastPage", articlePageInfo.isIsLastPage());
        //封装时间线对应的数据成jsonarray格式
          articleJsonArray  = timeLineToJsonArray(articleJsonArray,categoryList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",articleJsonArray);
        jsonObject.put("category",category);
        jsonObject.put("pageInfo",pageJson);


        return DataMap.success().setData(jsonObject);
    }

    @Override
    public DataMap findCategoriesNameAndArticleNum() {
   List<String> categorisName= categoriesMapper.findCategoriesNameAndArticleNum();

        JSONArray categoryJsonArray = new JSONArray();
        JSONObject categorJson = new JSONObject();
        for (String categoryName : categorisName) {
            categorJson = new JSONObject();
            categorJson.put("categoryName",categoryName);
            categorJson.put("categoryArticleNum",articleMapper.countArticleNumByCategor(categoryName) );
            categoryJsonArray.add(categorJson);

        }
        JSONObject returnjsonObject = new JSONObject();
        returnjsonObject.put("result",categoryJsonArray);


        return DataMap.success().setData(returnjsonObject);
    }

    @Override
    public DataMap getPageLeaveMessage(String pageName, String userName) {
        //获取留言
        List<LeaveMessageRecord> leaveMessageList = categoriesMapper.getPageLeaveMessage(pageName, 0);
        JSONObject leaveMessageJson;
        List<LeaveMessageRecord>LeaveMessageReplies;
        JSONArray leaveMessageJsonArray = new JSONArray();
        //循环留言表
        for (LeaveMessageRecord leaveMessage : leaveMessageList) {
            leaveMessageJson = new JSONObject();
            leaveMessageJson.put("avatarImgUrl", userService.findAvatarImgUrlByAnswereId(leaveMessage.getAnswererId()));
            leaveMessageJson.put("id", leaveMessage.getId());
            leaveMessageJson.put("answerer", userService.findUsernameByid(leaveMessage.getAnswererId()));
            leaveMessageJson.put("leaveMessageDate", leaveMessage.getLeaveMessageDate());
            leaveMessageJson.put("likes", leaveMessage.getLikes());
            leaveMessageJson.put("leaveMessageContent", leaveMessage.getLeaveMessageContent());
            if (null == userName){
                leaveMessageJson.put("isLiked",0);

            }else {
                leaveMessageJson.put("isLiked",1);
            }
            //查询回复留言列表
            JSONObject replyJson;

            JSONArray replyJsonArray = new JSONArray();

            LeaveMessageReplies = categoriesMapper.findLeaveMessageReplyByPageNameAndPid(pageName,leaveMessage.getId());
            for (LeaveMessageRecord leaveMessageReply : LeaveMessageReplies) {
                replyJson = new JSONObject();
                replyJson.put("id",leaveMessageReply.getId());
                replyJson.put("answerer",userService.findUsernameByid(leaveMessage.getAnswererId()));
                replyJson.put("respondent",userService.findUsernameByid(leaveMessage.getRespondentId()));
                replyJson.put("leaveMessageContent",leaveMessageReply.getLeaveMessageContent());
                replyJson.put("leaveMessageDate",leaveMessageReply.getLeaveMessageDate());
                replyJsonArray.add(replyJson);
            }
            leaveMessageJson.put("replies",replyJsonArray);
            leaveMessageJsonArray.add(leaveMessageJson);
        }
        JSONObject returnJson = new JSONObject();
        returnJson.put("result",leaveMessageJsonArray);

        return DataMap.success().setData(returnJson);
    }

    @Override
    public void publishLeaveMessage(String leaveMessageContent, String userName, String pageName) {
        //处理时间
        TimeUtil timeUtil = new TimeUtil();
        String newDateStr = timeUtil.getFormatDateForFive();
        //创建实体类
        LeaveMessageRecord leaveMessage = new LeaveMessageRecord(pageName,userService.getUserIdByuserName(userName),userService.getUserIdByuserName(userName),
                newDateStr,leaveMessageContent);
        categoriesMapper.publishLeaveMessage(leaveMessage);
        //更新redis中的数据
        addRedisNew(leaveMessage);
    }
     //在redis中增加一个未读的评论数
    private void addRedisNew(LeaveMessageRecord leaveMessage) {
        if(leaveMessage.getRespondentId() != leaveMessage.getAnswererId()){
            Boolean isExistKey = hashRedisServiceImpl.hasKey(leaveMessage.getRespondentId() + StringUtil.BLANK);
            if (!isExistKey){
                UserReadNews userReadNews = new UserReadNews(1,0,1);
                hashRedisServiceImpl.put(String.valueOf(leaveMessage.getRespondentId()),userReadNews,UserReadNews.class);

            }else {
                hashRedisServiceImpl.hashIncrement(leaveMessage.getRespondentId()+StringUtil.BLANK,"allNewsNum",1);
                hashRedisServiceImpl.hashIncrement(leaveMessage.getRespondentId()+StringUtil.BLANK,"leaveMessageNum",1);
            }
        }

    }


    private JSONArray timeLineToJsonArray(JSONArray articleJsonArray, List<Article> categoryList) {
        JSONObject articleJson;
        for (Article article : categoryList) {
            String[] tagsArray = StringAndArray.stringToArray(article.getArticleTags());
            articleJson = new JSONObject();
            articleJson.put("articleId",article.getArticleId());
            articleJson.put("articleTitle",article.getArticleTitle());
            articleJson.put("publishDate",article.getPublishDate());
            articleJson.put("articleCategories",article.getArticleCategories());
            articleJson.put("articleTags",tagsArray);
            articleJson.put("originalAuthor",article.getOriginalAuthor());
            articleJsonArray.add(articleJson);
        }
        return  articleJsonArray;
    }

}
