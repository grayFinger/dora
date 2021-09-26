package com.dora.common.http.okhttp.config;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import com.dora.common.config.HttpConfig;
import com.dora.common.http.ssl.SSLSocketClient;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OKHttp3Config {

    @Autowired
    private HttpConfig httpConfig;

    public OKHttp3Config() {
    }

    @Bean
    public ConnectionPool connectionPool() {
        long seconds = this.httpConfig.getKeepAliveTime().get(ChronoUnit.SECONDS);
        return new ConnectionPool(this.httpConfig.getIdleCount(), seconds, TimeUnit.SECONDS);
    }

    @Bean
    public OkHttpClient okHttpClient() {
        Builder builder = (new OkHttpClient()).newBuilder();
        builder.connectTimeout((long)this.httpConfig.getConnectTimeout(), TimeUnit.SECONDS).readTimeout((long)this.httpConfig.getReadTimeout(), TimeUnit.SECONDS).writeTimeout((long)this.httpConfig.getWriteTimeout(), TimeUnit.SECONDS).callTimeout((long)this.httpConfig.getReadTimeout(), TimeUnit.SECONDS).retryOnConnectionFailure(this.httpConfig.isRetryOnConnectionFailure()).followRedirects(this.httpConfig.isFollowRedirects()).followSslRedirects(this.httpConfig.isFollowRedirects()).connectionPool(this.connectionPool()).sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getTrustManager()).hostnameVerifier(SSLSocketClient.getHostnameVerifier());
        return builder.build();
    }
}
