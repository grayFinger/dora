package com.dora.common.execute;

import com.dora.common.http.IHttpClientResolver;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BatchHttpClient {
    private FutureHelperResolver futureHelperResolver;

    public BatchHttpClient(FutureHelper futureHelper) {
        this.futureHelperResolver = futureHelper.create();
    }

    public BatchHttpClientResolver addRequest(String name, IHttpClientResolver resolver) {
        return new BatchHttpClientResolver(name, resolver, this.futureHelperResolver);
    }

    public FutureHelperResolver getFutureHelperResolver() {
        return this.futureHelperResolver;
    }

    public <T> T get(String name) throws ExecutionException, InterruptedException {
        return this.futureHelperResolver.get(name);
    }

    public <T> T get(String name, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.futureHelperResolver.get(name, timeout, unit);
    }
}
