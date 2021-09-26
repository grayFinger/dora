package com.dora.common.lock;


import com.dora.common.lock.impl.SynchronizedLockHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@ConditionalOnProperty(
    value = {"lock.type"},
    havingValue = "sync",
    matchIfMissing = true
)
public class SynchronizedLockConfig {
    public SynchronizedLockConfig() {
    }

    @Bean
    public ILockHandler synchronizedLockHandler() {
        return new SynchronizedLockHandler();
    }
}
