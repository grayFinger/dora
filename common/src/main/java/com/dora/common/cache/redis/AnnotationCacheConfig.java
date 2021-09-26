//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dora.common.cache.redis;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

public class AnnotationCacheConfig extends CachingConfigurerSupport {
    private final RedisTemplate redisTemplate;

    @Autowired
    public AnnotationCacheConfig(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig().serializeKeysWith(SerializationPair.fromSerializer(this.redisTemplate.getKeySerializer())).serializeValuesWith(SerializationPair.fromSerializer(this.redisTemplate.getValueSerializer())).disableCachingNullValues();
        return RedisCacheManager.builder(this.redisTemplate.getConnectionFactory()).cacheDefaults(configuration).transactionAware().build();
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            return Arrays.asList(objects).toString();
        };
    }
}
