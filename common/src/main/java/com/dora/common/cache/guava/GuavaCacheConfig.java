package com.dora.common.cache.guava;


import com.dora.common.cache.ICache;
import com.google.common.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConditionalOnProperty(value = {"spring.redis.host"}, matchIfMissing = true)
@ConditionalOnClass({Cache.class})
public class GuavaCacheConfig {
    private static Logger logger = LoggerFactory.getLogger(GuavaCacheConfig.class);

    @Bean
    @ConfigurationProperties("spring.guava")
    @ConditionalOnMissingBean({GuavaConfig.class})
    public GuavaConfig guavaConfig() {
        return new GuavaConfig();
    }

    @Bean
    @ConditionalOnMissingBean({ICache.class})
    public ICache guavaCache() {
        logger.debug("初始化GuavaCache");
                GuavaCache guavaCache = new GuavaCache();
        guavaCache.setGuavaConfig(guavaConfig());
        return guavaCache;
    }
}
