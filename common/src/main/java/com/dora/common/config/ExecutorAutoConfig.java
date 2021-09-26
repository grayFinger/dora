package com.dora.common.config;


import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import com.dora.common.execute.FutureHelper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ExecutorAutoConfig implements AsyncConfigurer {
    public ExecutorAutoConfig() {
    }

    @Bean
    @ConfigurationProperties("thread.pool")
    public ExecutorConfig executorConfig() {
        return new ExecutorConfig();
    }

    @Bean
    public Executor taskExecutor(ExecutorConfig executorConfig) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(executorConfig.getCorePoolSize());
        executor.setMaxPoolSize(executorConfig.getMaxPoolSize());
        executor.setQueueCapacity(executorConfig.getQueueCapacity());
        executor.setThreadNamePrefix(executorConfig.getThreadNamePrefix() + "-");
        executor.setKeepAliveSeconds(executorConfig.getKeepAliveSeconds());
        executor.setRejectedExecutionHandler(new CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean
    public FutureHelper futureHelper(Executor taskExecutor) {
        return new FutureHelper(taskExecutor);
    }
}
