package com.kaka.service.Impl;

import com.kaka.mapper.UserMapper;
import com.kaka.redis.RedisService;
import com.kaka.utils.DataMap;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class RedisServiceImpl  {
    //增加redis中的访问量
    @Autowired
    private  HashRedisServiceImpl hashRedisServiceImpl;
    @Autowired
    private StringRedisServiceImpl stringRedisServiceImpl;
    @Autowired
    private UserMapper userMapper;

    public long addVisitorNumOnRedis(String visitor, Object field, long i) {
        boolean fieldIsExist = hashRedisServiceImpl.hasHashKey(visitor, field);
        if (fieldIsExist){
            return  hashRedisServiceImpl.hashIncrement(visitor,field,i);

        }
        return  0l;
    }
//从redis中查询访问量
    public long putVisitorNumOnRedis(String visitor, Object field, Object value) {
        hashRedisServiceImpl.put(visitor,field,value);
        return   Long.valueOf( hashRedisServiceImpl.get(visitor,field).toString());
    }

    public long getVisitorOnRedis(String visitor, String totalViisitor) {
        boolean fieldIsExist = hashRedisServiceImpl.hasHashKey(visitor, totalViisitor);
        if (fieldIsExist){
            return Long.valueOf( hashRedisServiceImpl.get(visitor,totalViisitor).toString());


        }
        return 0l;
    }
        //更新redis中的为未读量
    public void readThumbsUpOnRedis(String articleThumbsUp, int i) {
        Boolean readThumbsUpOnRedisIsExist = stringRedisServiceImpl.hasKey(articleThumbsUp);
        if (! readThumbsUpOnRedisIsExist){
            stringRedisServiceImpl.set(articleThumbsUp,1);


        }else {
            stringRedisServiceImpl.stringIncrement(articleThumbsUp,i);
        }
    }




    public DataMap getUserNews(String username) {
        //封装数据
        HashMap<String, Object> map = new HashMap<>();
        //根据当前登录用户查询id
        int userId = userMapper.getUserIdByuserName(username);
        //根据用户ID查询redis中的数据
        LinkedHashMap allFieldAndValueMap =(LinkedHashMap) hashRedisServiceImpl.getAllFieldAndValue(String.valueOf(userId));
        JSONObject jsonObject = new JSONObject();

        if (allFieldAndValueMap.size()==0){
            map.put("result",0);

        }else {
           int allNewsNum = (int)allFieldAndValueMap.get("allNewsNum");
            int commentNum = (int)allFieldAndValueMap.get("commentNum");
            int leaveMessageNum = (int)allFieldAndValueMap.get("leaveMessageNum");
            jsonObject.put("allNewsNum",allNewsNum);
            jsonObject.put("commentNum",commentNum);
            jsonObject.put("leaveMessageNum",leaveMessageNum);
            map.put("result",jsonObject);
        }
        return DataMap.success().setData(map);
    }
}
