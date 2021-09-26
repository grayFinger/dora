package com.dora.common.http;

public interface IHttpInterceptor {
    default boolean preHandler(IHttpClientResolver resolver, HttpRequest request) {
        return true;
    }

    default void postHandler(IHttpClientResolver resolver, HttpRequest request, HttpResponse response, Throwable throwable) {
    }
}
