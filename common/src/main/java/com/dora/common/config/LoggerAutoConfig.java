package com.dora.common.config;

import com.dora.common.log.DefaultLoggerContainer;
import com.dora.common.log.DefaultLoggerHandler;
import com.dora.common.log.ILoggerHandlerContainer;
import com.dora.common.log.LogService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerAutoConfig {
    public LoggerAutoConfig() {
    }

    @Bean
    @ConditionalOnMissingBean({ILoggerHandlerContainer.class})
    public ILoggerHandlerContainer loggerContainer() {
        DefaultLoggerContainer loggerContainer = new DefaultLoggerContainer();
        loggerContainer.addLoggerHandler(new DefaultLoggerHandler());
        return loggerContainer;
    }

    @Bean
    public LogService logService(ILoggerHandlerContainer loggerContainer) {
        LogService logService = new LogService();
        logService.setLoggerHandler(loggerContainer);
        return logService;
    }
}
