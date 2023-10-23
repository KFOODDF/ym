package com.kaka.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

// 定义此类为Spring的配置类
@Configuration
// 启用Spring的缓存支持
@EnableCaching
public class RedisConfig {

    // 定义一个Bean，用于创建Redis的缓存管理器
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 获取默认的缓存配置
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        // 使用非锁定的Redis缓存写入器创建缓存管理器，并设置默认配置
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    // 定义一个Bean，用于创建Redis模板，该模板可以用于执行Redis命令
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置连接工厂
        template.setConnectionFactory(factory);

        // 使用Jackson库创建JSON序列化器，用于序列化和反序列化Redis的值
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        // 设置可见性以及默认的类型信息
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // 创建字符串序列化器
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // 设置键的序列化方式为字符串序列化
        template.setKeySerializer(stringRedisSerializer);
        // 设置Hash键的序列化方式为字符串序列化
        template.setHashKeySerializer(stringRedisSerializer);
        // 设置值的序列化方式为Jackson JSON序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // 设置Hash值的序列化方式为Jackson JSON序列化
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 初始化模板
        template.afterPropertiesSet();

        return template;
    }
}


