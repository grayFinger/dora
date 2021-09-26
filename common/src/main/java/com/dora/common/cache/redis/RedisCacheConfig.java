package com.dora.common.cache.redis;

import com.dora.common.cache.ICache;
import com.dora.common.cache.ICacheSerializer;
import com.dora.common.cache.IQueue;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@ConditionalOnProperty(
    value = {"spring.redis.enable"},
    havingValue = "true"
)
@ConditionalOnClass({RedisTemplate.class})
@Import({AnnotationCacheConfig.class})
public class RedisCacheConfig {
    private static Logger logger = LoggerFactory.getLogger(RedisCacheConfig.class);
    @Autowired(
        required = false
    )
    private RedisTemplate redisTemplate;

    public RedisCacheConfig() {
    }

    @Bean
    @ConfigurationProperties("spring.redis")
    @ConditionalOnMissingBean({RedisConfig.class})
    public RedisConfig redisConfig() {
        logger.debug("初始化RedisConfig");
        return new RedisConfig();
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        om.enableDefaultTyping(DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean({RedisCache.class})
    public ICache redisCache(ICacheSerializer cacheSerializer, RedisConfig redisConfig) {
        logger.debug("初始化RedisCache");
        RedisCache cache = new RedisCache();
        cache.setConfig(redisConfig);
        cache.setRedisTemplate(this.redisTemplate);
        cache.setSerializer(cacheSerializer);
        return cache;
    }

    @Bean
    @ConditionalOnMissingBean({RedisQueue.class})
    public IQueue redisQueue(ICacheSerializer cacheSerializer, RedisConfig redisConfig) {
        logger.debug("初始化RedisQueue");
        RedisQueue queue = new RedisQueue();
        queue.setConfig(redisConfig);
        queue.setRedisTemplate(this.redisTemplate);
        queue.setSerializer(cacheSerializer);
        return queue;
    }

    @Bean
    @ConditionalOnMissingBean({RedisSet.class})
    public RedisSet redisSet(ICacheSerializer cacheSerializer, RedisConfig redisConfig) {
        logger.debug("初始化RedisSet");
        RedisSet redisSet = new RedisSet();
        redisSet.setConfig(redisConfig);
        redisSet.setRedisTemplate(this.redisTemplate);
        redisSet.setSerializer(cacheSerializer);
        return redisSet;
    }
}
