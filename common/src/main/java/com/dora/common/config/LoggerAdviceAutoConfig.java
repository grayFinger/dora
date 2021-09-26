package com.dora.common.config;

import com.dora.common.advice.LoggerAdvice;
import com.dora.common.log.LogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerAdviceAutoConfig {
    @Bean
    public LoggerAdvice loggerAdvice(LogService logService) {
        LoggerAdvice loggerAdvice = new LoggerAdvice();
        loggerAdvice.setLogService(logService);
        return loggerAdvice;
    }
}
