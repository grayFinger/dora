package com.dora.common.cache.redis;

import com.dora.common.cache.AbstractCache;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

public class AbstractRedis extends AbstractCache {
    protected RedisConfig config;
    protected RedisTemplate redisTemplate;

    public AbstractRedis() {
    }

    public RedisConfig getConfig() {
        return this.config;
    }

    public RedisTemplate getRedisTemplate() {
        return this.redisTemplate;
    }

    public void setConfig(RedisConfig config) {
        this.config = config;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getKey(String key) {
        if (this.config != null) {
            String ns = this.config.getNamespace();
            if (StringUtils.isNotEmpty(ns)) {
                return ns + "::" + key;
            }
        }

        return key;
    }
}
