package com.kaka.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaka.mapper.ArticleMapper;
import com.kaka.mapper.LikesMapper;
import com.kaka.model.ArticleLikesRecord;
import com.kaka.service.LikesService;
import com.kaka.utils.DataMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikesServiceImpl implements LikesService {
    @Autowired
    private LikesMapper likesMapper;
        @Autowired
        private ArticleMapper articleMapper;
    @Override
    public DataMap getArticleThumbsUp(int rows, int pageNum) {
        //开启分页插件
        PageHelper.startPage(pageNum, rows);
        List<ArticleLikesRecord> likesRecord = likesMapper.getArticleThumbsUp();
        PageInfo<ArticleLikesRecord> pageInfo = new PageInfo<>(likesRecord);
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject articleLikeJson;
        for (ArticleLikesRecord articleLikesRecord : likesRecord) {
            articleLikeJson = new JSONObject();
            articleLikeJson.put("id", articleLikesRecord.getId());
            articleLikeJson.put("articleId", articleLikesRecord.getArticleId());
            articleLikeJson.put("articleTitle", articleMapper.getArticleTitleByArtitleId(articleLikesRecord.getArticleId()));
            articleLikeJson.put("likeDate", articleLikesRecord.getLikeDate());
            articleLikeJson.put("praisePeople", articleMapper.getArticleAuthorByArtitleId(articleLikesRecord.getArticleId()));
            articleLikeJson.put("isRead", articleLikesRecord.getArticleId());
            jsonArray.add(articleLikeJson);

        }
        returnJson.put("result", jsonArray);
        returnJson.put("magIsNotReadNum",likesMapper.getMagIsNotReadNum());
        //封装最外层数据
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum", pageInfo.getPageNum());
        pageJson.put("pageSize", pageInfo.getSize());
        pageJson.put("total", pageInfo.getTotal());
        pageJson.put("pages", pageInfo.getPages());
        pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
        pageJson.put("isLastPage", pageInfo.isIsLastPage());
        returnJson.put("pageInfo", pageJson);

        return DataMap.success().setData(returnJson);
    }
}