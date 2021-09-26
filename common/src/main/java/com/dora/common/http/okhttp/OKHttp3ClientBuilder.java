package com.dora.common.http.okhttp;

import com.dora.common.config.HttpConfig;
import com.dora.common.http.IHttpClientBuilder;
import com.dora.common.http.IHttpClientResolver;
import com.dora.common.http.interceptor.LoggerInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class OKHttp3ClientBuilder implements IHttpClientBuilder {
    @Autowired
    private HttpConfig httpConfig;
    @Autowired
    private OkHttpClient okHttpClient;
    @Autowired
    private LoggerInterceptor loggerInterceptor;
    @Value("${hystrix.enable:false}")
    private boolean hystrixEnabled;

    public OKHttp3ClientBuilder() {
    }

    public IHttpClientResolver client() {
        OkHttp3ClientResolver resolver = new OkHttp3ClientResolver(this.okHttpClient);
        if (this.httpConfig != null) {
            resolver.setHystrixEnabled(this.hystrixEnabled);
        }

        if (this.loggerInterceptor != null) {
            resolver.interceptor(this.loggerInterceptor);
        }

        return resolver;
    }
}
