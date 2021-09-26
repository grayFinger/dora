package com.dora.common.config;

import com.dora.common.cache.CacheService;
import com.dora.common.cache.ICache;
import com.dora.common.cache.ICacheSerializer;
import com.dora.common.cache.guava.GuavaCacheConfig;
import com.dora.common.cache.redis.RedisCacheConfig;
import com.dora.common.cache.serializer.JSONCacheSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableCaching
@Import({RedisCacheConfig.class, GuavaCacheConfig.class})
public class CacheAutoConfig {
    private static Logger logger = LoggerFactory.getLogger(CacheAutoConfig.class);

    public CacheAutoConfig() {
    }

    @Bean
    @ConditionalOnMissingBean({ICacheSerializer.class})
    public ICacheSerializer cacheSerializer() {
        return new JSONCacheSerializer();
    }

    @Bean
    @ConditionalOnBean({ICache.class})
    @ConditionalOnMissingBean({CacheService.class})
    public CacheService cacheService(ICache cache) {
        logger.debug("初始化缓存对象，cache：{}", cache);
        CacheService cacheService = new CacheService();
        cacheService.setCache(cache);
        return cacheService;
    }
}
