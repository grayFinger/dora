package com.dora.common.http;

import com.dora.common.utils.ClassUtils;
import com.dora.common.utils.JsonUtils;
import com.netflix.hystrix.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HttpHystrixCommand<T> extends HystrixCommand<T> {
    private static Logger logger = LoggerFactory.getLogger(HttpHystrixCommand.class);
    private IHttpFallback<T> httpFallback;
    private IHttpClientResolver resolver;
    private IHttpDecoder decoder;

    public HttpHystrixCommand(IHttpFallback<T> httpFallback, IHttpClientResolver resolver, IHttpDecoder decoder) {
        super(httpFallback.getCommandSetter());
        this.httpFallback = httpFallback;
        this.resolver = resolver;
        this.decoder = decoder;
    }

    protected T getFallback() {
        logger.debug("调用接口[{}]出现异常，执行接口容错处理，处理fallback:{}", this.resolver.url(), this.httpFallback.getClass());

        try {
            Object body = this.httpFallback.fallback(this.resolver);
            logger.debug("接口[{}]容错处理之后的数据为：{}", this.resolver.url(), body);
            if (body == null) {
                return null;
            } else {
                Class<?> genericClass = ClassUtils.getGenericClass(this.decoder.getClass(), 1);
                return genericClass != null && String.class.isAssignableFrom(genericClass) ? (T) JsonUtils.toJson(body) : (T) body;
            }
        } catch (Exception var3) {
            logger.error("获取容错数据失败", var3);
            return null;
        }
    }

    protected String getCacheKey() {
        return super.getCacheKey();
    }
}
