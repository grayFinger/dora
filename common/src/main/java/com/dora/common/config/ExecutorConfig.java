//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dora.common.config;

public class ExecutorConfig {
    private int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
    private int maxPoolSize = 10240;
    private int queueCapacity = 200;
    private int keepAliveSeconds = 300;
    private String threadNamePrefix = "cc";

    public ExecutorConfig() {
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }

    public int getQueueCapacity() {
        return this.queueCapacity;
    }

    public int getKeepAliveSeconds() {
        return this.keepAliveSeconds;
    }

    public String getThreadNamePrefix() {
        return this.threadNamePrefix;
    }
}
