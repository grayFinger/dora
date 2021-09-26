package com.dora.common.config;

import com.dora.common.exception.wrap.ExceptionWrapper;
import com.dora.common.utils.ExceptionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionAutoConfig {
    public ExceptionAutoConfig() {
    }

    @Bean
    @ConditionalOnMissingBean({ExceptionWrapper.class})
    public ExceptionWrapper exceptionWrapper() {
        ExceptionWrapper exceptionWrapper = new ExceptionWrapper();
        ExceptionUtils.setExceptionWrapper(exceptionWrapper);
        return exceptionWrapper;
    }
}
