package com.dora.common.http;

import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.HystrixCommandGroupKey.Factory;

public abstract class AbstractHttpFallback<T> implements IHttpFallback<T> {
    protected Setter setter;

    public AbstractHttpFallback(String groupKey, String commandKey) {
        this.setter = Setter.withGroupKey(Factory.asKey(groupKey));
        if (commandKey != null) {
            this.setter.andCommandKey(com.netflix.hystrix.HystrixCommandKey.Factory.asKey(commandKey));
        }

    }

    public T fallback(IHttpClientResolver resolver) throws Exception {
        T result = this.getData(resolver);
        if (result == null) {
            result = this.getDefaultData(resolver);
        }

        return result;
    }

    protected T getData(IHttpClientResolver resolver) throws Exception {
        return null;
    }

    protected T getDefaultData(IHttpClientResolver resolver) throws Exception {
        return null;
    }

    public AbstractHttpFallback(Setter setter) {
        this.setter = setter;
    }

    public Setter getCommandSetter() {
        return this.setter;
    }
}
