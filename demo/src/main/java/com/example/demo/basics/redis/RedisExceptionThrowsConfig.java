package com.example.demo.basics.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.Objects;

@ApiOperation(value = "Redis异常处理")
@Slf4j
public class RedisExceptionThrowsConfig extends CachingConfigurerSupport {
    private String unit = "day";

    private Integer time = 30;

    @Override
    @ApiOperation(value = "Redis序列化异常")
    public CacheErrorHandler errorHandler() {
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {

            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                log.warn("Redis序列化出现了查询了异常");
                log.warn(key.toString());
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                log.warn("Redis序列化出现了插入异常");
                log.warn(key.toString());
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                log.warn("Redis序列化出现了Evict异常");
                log.warn(key.toString());
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                log.warn("Redis序列化出现了删除异常");
                log.warn(exception.toString());
            }
        };
        return cacheErrorHandler;
    }

    @Bean
    @ApiOperation(value = "Redis序列化")
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        // 判断缓存格式化错误
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        PolymorphicTypeValidator ptv = new ObjectMapper().getPolymorphicTypeValidator();
        objectMapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        // 判断乱码错误
        RedisCacheConfiguration rc = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                .disableCachingNullValues();
        // 处理缓存时长
        Duration expireTime = Duration.ofDays(time);
        if(Objects.equals(unit,"hour")) {
            expireTime = Duration.ofHours(time);
        } else if(Objects.equals(unit,"minute")) {
            expireTime = Duration.ofMinutes(time);
        }
        return RedisCacheManager.builder(factory).cacheDefaults(rc.entryTtl(expireTime)).build();
    }
}
