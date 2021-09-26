package com.dora.commonservice.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * Redis配置
 * @Author: zhaolianqi
 * @Date: 2020/10/29 15:17
 * @Version: v1.0
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonConfiguration {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private String port;
    @Value("${spring.redis.password:}")
    private String password;

    @Bean
    public RedissonClient getRedissonClient() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer();
        if (!StringUtils.isEmpty(password))
            serverConfig.setPassword(password);
        serverConfig.setAddress("redis://" + host + ":" + port);
        config.setCodec(new JsonJacksonCodec());
        return Redisson.create(config);
    }

}
