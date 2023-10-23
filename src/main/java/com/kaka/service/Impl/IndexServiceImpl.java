package com.kaka.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaka.constant.CodeType;
import com.kaka.mapper.*;
import com.kaka.model.CommentRecord;
import com.kaka.model.LeaveMessageRecord;
import com.kaka.model.Tags;
import com.kaka.service.IndexService;
import com.kaka.utils.DataMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private IndexMapper indexMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private  CommentRecordMapper commentRecordMapper;
    @Autowired
    private  LeaveMessageRecordMapper leaveMessageRecordMapper;

    @Override
    public DataMap getSiteInfo() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("articleNum",articleMapper.countArticleNum());
        map.put("tagsNum",tagMapper.countTagsNum());
        map.put("leaveWordNum",leaveMessageRecordMapper.countLeaveMessageMapper());
        map.put("commentNum",commentRecordMapper.countCommentMapper());
        return DataMap.success().setData(map);
    }
//获取最新的评论
    @Override
    public DataMap getNewComment(int rows, int pageNum) {
        // 使用分页插件进行分页
        PageHelper.startPage(pageNum, rows);
            //创建一个json对象
        JSONObject resultJson = new JSONObject();

        //查询评论
        List<CommentRecord> comments= commentRecordMapper.queryComment();
        //查询完以后要把查询后的对象交给pageinfo管理
        PageInfo<CommentRecord> commentRecordPageInfo = new PageInfo<>(comments);

        JSONArray jsonArray = new JSONArray();

        //遍历查询的评论结果
        for (CommentRecord comment : comments) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("commentContent",comment.getCommentContent());
            jsonObject.put("id",comment.getId());
            jsonObject.put("answerer",userMapper.findUsernameByid(comment.getAnswererId()));
            jsonObject.put("articleId",comment.getArticleId());
            jsonObject.put("commentDate",comment.getCommentDate());
            jsonObject.put("articleTitle",articleMapper.getArticleTitleByArtitleId(comment.getArticleId()));
                jsonArray.add(jsonObject);
        }
        resultJson.put("result",jsonArray);
        //封装分页数据
        // 封装分页信息到JSON对象中
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum", commentRecordPageInfo.getPageNum());
        pageJson.put("pageSize", commentRecordPageInfo.getSize());
        pageJson.put("total", commentRecordPageInfo.getTotal());
        pageJson.put("pages", commentRecordPageInfo.getPages());
        pageJson.put("isFirstPage", commentRecordPageInfo.isIsFirstPage());
        pageJson.put("isLastPage", commentRecordPageInfo.isIsLastPage());
        resultJson.put("pageInfo", pageJson);

        // 返回成功的DataMap，并将数据封装进去
        return DataMap.success().setData(resultJson);

    }

    @Override
    public DataMap getNewLeaveWord(int rows, int pageNum) {
        // 使用分页插件进行分页
        PageHelper.startPage(pageNum, rows);
        //创建一个json对象
        JSONObject resultJson = new JSONObject();

        //查询留言
        List<LeaveMessageRecord> leaveMessages =leaveMessageRecordMapper.queryLeaveMessage();
        //查询完以后要把查询后的对象交给pageinfo管理
        PageInfo<LeaveMessageRecord> commentRecordPageInfo = new PageInfo<>(leaveMessages);

        JSONArray jsonArray = new JSONArray();


        //遍历查询的评论结果
        for (LeaveMessageRecord leaveMessage : leaveMessages) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",leaveMessage.getId());
            jsonObject.put("pagePath",leaveMessage.getPageName());
            jsonObject.put("leaveWordContent",leaveMessage.getLeaveMessageContent());
            jsonObject.put("answerer",userMapper.findUsernameByid(leaveMessage.getAnswererId()));
            jsonObject.put("leaveWordDate",leaveMessage.getLeaveMessageDate());
            jsonArray.add(jsonObject);
        }
        resultJson.put("result",jsonArray);
        //封装分页数据
        // 封装分页信息到JSON对象中
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum", commentRecordPageInfo.getPageNum());
        pageJson.put("pageSize", commentRecordPageInfo.getSize());
        pageJson.put("total", commentRecordPageInfo.getTotal());
        pageJson.put("pages", commentRecordPageInfo.getPages());
        pageJson.put("isFirstPage", commentRecordPageInfo.isIsFirstPage());
        pageJson.put("isLastPage", commentRecordPageInfo.isIsLastPage());
        resultJson.put("pageInfo", pageJson);

        // 返回成功的DataMap，并将数据封装进去
        return DataMap.success().setData(resultJson);


    }

    @Override
    public DataMap findTagsCloud() {
        //查询标签
        List<Tags> tags=tagMapper.findTagsCloud();
        //封装数据
        HashMap<String, Object> map = new HashMap<>();

        map.put("result",JSONArray.fromObject(tags));
        return DataMap.success(CodeType.FIND_TAGS_CLOUD).setData(map);


    }
}
