package com.dora.common.http;

import com.netflix.hystrix.HystrixCommand.Setter;

public interface IHttpFallback<T> {
    Setter getCommandSetter();

    T fallback(IHttpClientResolver resolver) throws Exception;

    default void onSuccess(IHttpClientResolver resolver, T result) throws Exception {
    }
}
