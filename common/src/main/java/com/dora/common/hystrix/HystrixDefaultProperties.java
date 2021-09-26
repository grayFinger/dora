package com.dora.common.hystrix;

import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import java.util.HashMap;
import java.util.Map;

public class HystrixDefaultProperties {
    private static Map<String, Object> props = new HashMap();

    public HystrixDefaultProperties() {
    }

    static {
        props.put("hystrix.command.default.circuitBreaker.enabled", true);
        props.put("hystrix.command.default.circuitBreaker.errorThresholdPercentage", 50);
        props.put("hystrix.command.default.circuitBreaker.forceClosed", false);
        props.put("hystrix.command.default.circuitBreaker.forceOpen", false);
        props.put("hystrix.command.default.circuitBreaker.requestVolumeThreshold", 20);
        props.put("hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds", 10000);
        props.put("hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests", 10);
        props.put("hystrix.command.default.execution.isolation.strategy", ExecutionIsolationStrategy.THREAD);
        props.put("hystrix.command.default.execution.isolation.thread.interruptOnTimeout", true);
        props.put("hystrix.command.default.execution.isolation.thread.interruptOnFutureCancel", false);
        props.put("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", 1000);
        props.put("hystrix.command.default.execution.timeout.enabled", true);
        props.put("hystrix.command.default.fallback.isolation.semaphore.maxConcurrentRequests", 10);
        props.put("hystrix.command.default.fallback.enabled", true);
        props.put("hystrix.command.default.metrics.healthSnapshot.intervalInMilliseconds", 500);
        props.put("hystrix.command.default.metrics.rollingPercentile.bucketSize", 100);
        props.put("hystrix.command.default.metrics.rollingPercentile.enabled", true);
        props.put("hystrix.command.default.metrics.rollingPercentile.timeInMilliseconds", 60000);
        props.put("hystrix.command.default.metrics.rollingPercentile.numBuckets", 6);
        props.put("hystrix.command.default.metrics.rollingStats.timeInMilliseconds", 10000);
        props.put("hystrix.command.default.metrics.rollingStats.numBuckets", 10);
        props.put("hystrix.command.default.requestCache.enabled", true);
        props.put("hystrix.command.default.requestLog.enabled", true);
        props.put("hystrix.threadpool.default.coreSize", 10);
        props.put("hystrix.threadpool.default.maximumSize", 10);
        props.put("hystrix.threadpool.default.keepAliveTimeMinutes", 1);
        props.put("hystrix.threadpool.default.maxQueueSize", -1);
        props.put("hystrix.threadpool.default.queueSizeRejectionThreshold", 5);
        props.put("hystrix.threadpool.default.allowMaximumSizeToDivergeFromCoreSize", false);
        props.put("hystrix.threadpool.default.metrics.rollingStats.timeInMilliseconds", 5000);
        props.put("hystrix.threadpool.default.metrics.rollingStats.numBuckets", 10);
    }
}
