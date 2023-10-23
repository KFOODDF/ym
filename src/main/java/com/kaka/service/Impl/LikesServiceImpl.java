package com.kaka.service.Impl;

// 导入相关的类和包
import com.github.pagehelper.PageHelper;  // 用于数据库查询的分页操作的插件。
import com.github.pagehelper.PageInfo;  // 用于封装分页查询结果的类。
import com.kaka.constant.CodeType;  // 可能包含了一些状态码或消息常量的类。
import com.kaka.mapper.ArticleMapper;  // 用于数据库中文章数据的CRUD操作的数据访问层接口。
import com.kaka.mapper.LikesMapper;  // 用于数据库中点赞数据的CRUD操作的数据访问层接口。
import com.kaka.mapper.UserMapper;
import com.kaka.model.ArticleLikesRecord;  // 定义了点赞记录的属性和方法的文章点赞记录数据模型。
import com.kaka.model.Friendlink;  // 友情链接的数据模型。
import com.kaka.service.LikesService;  // 定义了点赞功能的方法的点赞服务接口。
import com.kaka.service.UserService;
import com.kaka.utils.DataMap;  // 用于数据的转换或封装的数据映射工具类。
import net.sf.json.JSONArray;  // 用于处理JSON格式的数组数据的JSON数组处理类。
import net.sf.json.JSONObject;  // 用于处理JSON格式的对象数据的JSON对象处理类。
import org.springframework.beans.factory.annotation.Autowired;  // 用于自动注入依赖的bean的Spring的自动注入注解。
import org.springframework.stereotype.Service;  // 表示这是一个Spring服务类的注解。
import org.springframework.transaction.annotation.Transactional;  // 用于声明某个方法需要进行事务管理的事务注解。

import java.util.List;  // 用于存储和处理一系列对象的List集合接口。

// 使用@Service注解，告诉Spring这是一个服务类。
@Service
public class LikesServiceImpl implements LikesService {

    // 使用@Autowired注解，告诉Spring自动注入LikesMapper对象。
    @Autowired
    private LikesMapper likesMapper;

    // 使用@Autowired注解，告诉Spring自动注入ArticleMapper对象。
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    // 实现LikesService接口中定义的获取文章点赞信息的方法。
    @Override
    public DataMap getArticleThumbsUp(int rows, int pageNum) {
        // 使用分页插件进行分页查询。
        PageHelper.startPage(pageNum, rows);

        // 从数据库中查询文章点赞记录列表。
        List<ArticleLikesRecord> likesRecord = likesMapper.getArticleThumbsUp();
        PageInfo<ArticleLikesRecord> pageInfo = new PageInfo<>(likesRecord);

        // 创建JSON对象和数组，用于封装返回的数据。
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        // 遍历点赞记录列表，将每条记录的信息封装到JSON对象中，然后添加到JSON数组中。
        for (ArticleLikesRecord articleLikesRecord : likesRecord) {
            JSONObject articleLikeJson = new JSONObject();
            articleLikeJson.put("id", articleLikesRecord.getId());
            articleLikeJson.put("articleId", articleLikesRecord.getArticleId());
            articleLikeJson.put("articleTitle", articleMapper.getArticleTitleByArtitleId(articleLikesRecord.getArticleId()));
            articleLikeJson.put("likeDate", articleLikesRecord.getLikeDate());
            articleLikeJson.put("praisePeople", articleMapper.getArticleAuthorByArtitleId(articleLikesRecord.getArticleId()));
            articleLikeJson.put("isRead", articleLikesRecord.getArticleId());
            jsonArray.add(articleLikeJson);
        }

        // 将点赞记录的JSON数组添加到返回的JSON对象中。
        returnJson.put("result", jsonArray);

        // 获取未读消息数量，并添加到返回的JSON对象中。
        returnJson.put("magIsNotReadNum",likesMapper.getMagIsNotReadNum());

        // 封装分页信息到JSON对象中。
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum", pageInfo.getPageNum());
        pageJson.put("pageSize", pageInfo.getSize());
        pageJson.put("total", pageInfo.getTotal());
        pageJson.put("pages", pageInfo.getPages());
        pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
        pageJson.put("isLastPage", pageInfo.isIsLastPage());
        returnJson.put("pageInfo", pageJson);

        // 返回成功的DataMap，并将数据封装进去。
        return DataMap.success().setData(returnJson);
    }

    // 实现LikesService接口中定义的读取所有点赞信息的方法。
    @Override
    public DataMap readAllThumbsUp() {
        likesMapper.readAllThumbsUp();
        // TODO: 删除redis中的点赞消息
        return DataMap.success();
    }

    // 实现LikesService接口中定义的获取友情链接的方法。
    @Override
    public DataMap getFriendLink() {
        List<Friendlink> friendLinks = likesMapper.getFriendLink();

        return DataMap.success().setData(friendLinks);
    }

    // 实现LikesService接口中定义的添加友情链接的方法，并使用@Transactional注解声明这是一个事务方法。
    @Transactional
    @Override
    public DataMap addFriendLink(Friendlink friendlink) {
        // 从严格意义上说，这里可以加入检查友情链接是否已存在的逻辑。
        //int id = likesMapper.findIsExistByBlogger(friendlink.getBlogger());
        likesMapper.addFriendlink(friendlink);
        return DataMap.success(CodeType.ADD_FRIEND_LINK_SUCCESS).setData(friendlink.getId());
    }

    // 实现LikesService接口中定义的更新友情链接的方法。
    @Override
    public DataMap updateFriendLink(Friendlink friendlink) {
        likesMapper.updateFriendLink(friendlink);
        return DataMap.success(CodeType.UPDATE_FRIEND_LINK_SUCCESS).setData(friendlink.getId());
    }

    // 实现LikesService接口中定义的删除友情链接的方法。
    @Override
    public DataMap deleteFriendLink(String id) {
        likesMapper.deleteFriendLink(id);
        return DataMap.success(CodeType.DELETE_FRIEND_LINK_SUCCESS);
    }

    @Override
    public boolean isLiked(Long articleId, String name) {
      ArticleLikesRecord articleLikesRecord=  likesMapper.isLiked(articleId,userMapper.getUserIdByuserName(name));
        return articleLikesRecord !=null;
    }

    @Override
    public DataMap updateLikeByArticleId(String articleId) {
        articleMapper.updateLikeByArticleId(articleId);
        int liks= articleMapper.queryLiksByarticle(articleId);
        return DataMap.success().setData(liks);
    }

    @Override
    public void inertArticleLikesRecord(ArticleLikesRecord articleLikesRecord) {
        likesMapper.inertArticleLikesRecord(articleLikesRecord);
    }


}
