package com.dora.common.config;

import com.dora.common.http.HttpClient;
import com.dora.common.http.IHttpClientBuilder;
import com.dora.common.http.interceptor.LoggerInterceptor;
import com.dora.common.http.okhttp.OKHttp3ClientBuilder;
import com.dora.common.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpAutoConfig {
    @Autowired(
        required = false
    )
    private LogService logService;
    @Autowired(
        required = false
    )
    private AppConfig appConfig;

    public HttpAutoConfig() {
    }

    @Bean
    @ConditionalOnMissingBean({LoggerInterceptor.class})
    public LoggerInterceptor loggerInterceptor() {
        LoggerInterceptor interceptor = new LoggerInterceptor();
        interceptor.setLogService(this.logService);
        interceptor.setAppConfig(this.appConfig);
        return interceptor;
    }

    @Bean
    @ConfigurationProperties(
        prefix = "spring.http"
    )
    public HttpConfig httpConfig() {
        return new HttpConfig();
    }

    @Bean
    @ConditionalOnMissingBean({IHttpClientBuilder.class})
    public IHttpClientBuilder httpClientBuilder() {
        return new OKHttp3ClientBuilder();
    }

    @Bean
    @ConditionalOnMissingBean({HttpClient.class})
    public HttpClient httpClient(IHttpClientBuilder httpClientBuilder) {
        HttpClient httpClient = new HttpClient();
        httpClient.setHttpClientBuilder(httpClientBuilder);
        return httpClient;
    }
}
