package com.dora.common.http;

public class HttpClient {
    private IHttpClientBuilder httpClient;

    public HttpClient() {
    }

    public IHttpClientResolver client() {
        return this.httpClient == null ? null : this.httpClient.client();
    }

    public void setHttpClientBuilder(IHttpClientBuilder httpClient) {
        this.httpClient = httpClient;
    }
}
