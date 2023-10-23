package com.kaka.service.Impl;

import com.kaka.mapper.ArticleMapper;
import com.kaka.mapper.UserMapper;
import com.kaka.mapper.VisitorMapper;
import com.kaka.model.Visitor;
import com.kaka.redis.RedisService;
import com.kaka.service.StatisticsService;
import com.kaka.service.UserService;
import com.kaka.utils.DataMap;
import com.kaka.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
public class StatisticsImpl implements StatisticsService {
    //总访问量
    private static final String TOTAL_VISITRor = "totalVisitor";
    //当前页面访问量
    private static final String PAGE_VISITRor = "pageVisitor";

    @Autowired
    private RedisServiceImpl redisServiceImpl;
    @Autowired
    private VisitorMapper visitorMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StringRedisServiceImpl stringRedisServiceImpl;

    @Override
    public DataMap getVisitorNumByPageName(HttpServletRequest request, String pageName) {
        //创建一个map
        HashMap<String, Object> dataMap = new HashMap<>();
        //获取当前放分页面的visitor
        String visitor = (String) request.getSession().getAttribute(pageName);
        //判断当前session生命周期中是否浏览过当前pagname   若浏览过则增加访问量
        if (visitor == null) {
            request.getSession().setAttribute(pageName, "yes");
        }
        //增加当前页面的访问人数
        long pageVistor = updatePageVisitor(visitor, pageName);
        //增加总访问人数
        long totalvisitor = redisServiceImpl.addVisitorNumOnRedis(StringUtil.VISITOR, TOTAL_VISITRor, 1);
        if (totalvisitor == 0) {
            totalvisitor = visitorMapper.getTotalVisitor();//为什么数据库会有数据
            //redis中要去put一下
            totalvisitor = redisServiceImpl.putVisitorNumOnRedis(StringUtil.VISITOR, TOTAL_VISITRor, totalvisitor+1);
        }
        dataMap.put(TOTAL_VISITRor, totalvisitor);
        dataMap.put(PAGE_VISITRor, pageVistor);

        return DataMap.success().setData(dataMap);
    }

    @Override
    public DataMap getStatisticsInfo(HttpServletRequest request) {
        HashMap<String, Object> dataMap = new HashMap<>();
       //总访问量
        long allVisitor =  redisServiceImpl.getVisitorOnRedis(StringUtil.VISITOR,"totalVisitor");
           //昨日访问量
        long yesterdayVisitor =  redisServiceImpl.getVisitorOnRedis(StringUtil.VISITOR,"yesterdayVisitor");

        dataMap.put("allVisitor",allVisitor );
        dataMap.put("yesterdayVisitor",yesterdayVisitor );
        dataMap.put("allUser",userMapper.countUserNum() );
        dataMap.put("articleNum",articleMapper.countArticleNum());
        //查询点赞量todo
     if (stringRedisServiceImpl.hasKey(StringUtil.ARTICLE_THUMBS_UP)){
        int thumsbsNum =(int) stringRedisServiceImpl.get(StringUtil.ARTICLE_THUMBS_UP);
            dataMap.put("articleThumbsUpNum",thumsbsNum);
     }else {
         dataMap.put("articleThumbsUpNum",0);
     }
        return DataMap.success().setData(dataMap);
    }






    //更新当前页面访问量
    private long updatePageVisitor(String visitor, String pageName) {
        Visitor thisVisitor;
        Long pageVistitor;
        //session生命周期内没有浏览器改page。则增加访问量
        if (visitor == null) {
            pageVistitor = redisServiceImpl.addVisitorNumOnRedis(StringUtil.VISITOR, pageName, 1);


            //如果redis中没有命中，则去数据库中去
            if (pageVistitor == 0) {

                thisVisitor = visitorMapper.getVisitorNumByPageName(pageName);
                if (thisVisitor != null) {

                    redisServiceImpl.putVisitorNumOnRedis(StringUtil.VISITOR, pageName, thisVisitor.getVisitorNum() + 1);

                } else {
                    return 0l;
                }
            }
        }
        return redisServiceImpl.getVisitorOnRedis(StringUtil.VISITOR,pageName);
    }
}