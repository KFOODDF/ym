package com.kaka.redis;

import com.kaka.utils.DataMap;
import org.springframework.stereotype.Service;

@Service
public interface RedisService {
    Boolean hasKey(String Key);


}
