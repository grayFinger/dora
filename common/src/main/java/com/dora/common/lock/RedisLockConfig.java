package com.dora.common.lock;

import com.dora.common.cache.redis.RedisConfig;
import com.dora.common.lock.impl.RedisLockHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

@ConditionalOnProperty(
    value = {"lock.type"},
    havingValue = "redis"
)
@ConditionalOnClass({RedisTemplate.class})
public class RedisLockConfig {
    @Autowired(
        required = false
    )
    private RedisTemplate redisTemplate;
    @Autowired(
        required = false
    )
    private RedisConfig redisConfig;

    public RedisLockConfig() {
    }

    @Bean
    public ILockHandler removeLockHandler() {
        RedisLockHandler lockHandler = new RedisLockHandler();
        lockHandler.setConfig(this.redisConfig);
        lockHandler.setRedisTemplate(this.redisTemplate);
        return lockHandler;
    }
}
