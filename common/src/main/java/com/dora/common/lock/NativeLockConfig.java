package com.dora.common.lock;

import com.dora.common.lock.impl.NativeLockHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@ConditionalOnProperty(
    value = {"lock.type"},
    havingValue = "rw"
)
public class NativeLockConfig {
    public NativeLockConfig() {
    }

    @Bean
    public ILockHandler nativeLockHandler() {
        return new NativeLockHandler();
    }
}
