package com.kaka.config;

import com.kaka.mapper.VisitorMapper;
import com.kaka.service.Impl.HashRedisServiceImpl;
import com.kaka.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

//用定时任务统计数据到redis
@Component
public class ScheduledTask {
    @Autowired
    private VisitorMapper visitorMapper;
    @Autowired
    private HashRedisServiceImpl hashRedisServiceImpl;

    //
    @Scheduled(cron = "0 0 0 * * ?")
    public void  statisticsVisitorNum(){
        //查询出旧的总访问量
        long oldTotalVisitor = visitorMapper.getTotalVisitor();
        //查询当前最新的总访问量
       Long newTotalVisitor =Long.valueOf(hashRedisServiceImpl.get(StringUtil.VISITOR, "totalVisitor").toString());
        //作差获取到昨日访问量
      long yesterdayVisitor =  newTotalVisitor - oldTotalVisitor;
      if (hashRedisServiceImpl.hasHashKey(StringUtil.VISITOR,"yesterdayVisitor")){
          hashRedisServiceImpl.put(StringUtil.VISITOR,"yesterdayVisitor",yesterdayVisitor);

      }else {
          hashRedisServiceImpl.put(StringUtil.VISITOR,"yesterdayVisitor",oldTotalVisitor);
      }
      //将redis中的所有访客记录更新到数据库中
        LinkedHashMap map  =(LinkedHashMap) hashRedisServiceImpl.getAllFieldAndValue(StringUtil.VISITOR);
      String pageName;
        for (Object o : map.keySet()){
          pageName =  String.valueOf(o);
          visitorMapper.updateVisitorBypageName(pageName,String.valueOf(map.get(o)));
          if (!"totalVisitor".equals(pageName) && "yesterdayVisitor".equals(pageName)  ){
                hashRedisServiceImpl.hashDelete(StringUtil.VISITOR,pageName);

          }
        }
    }
}
