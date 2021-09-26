package com.dora.common.execute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureHelperResolver {
    private Executor executor;
    private Map<String, ExecutorCompletableFuture> futureMap = new HashMap();
    private List<ExecutorCompletableFuture> futures = new ArrayList();

    public FutureHelperResolver(Executor executor) {
        this.executor = executor;
    }

    public FutureHelperResolver supplyAsync(String name, ExSupplier supplier) {
        ExecutorCompletableFuture future = ExecutorCompletableFuture.supplyAsync(supplier, this.executor);
        this.futureMap.put(name, future);
        this.futures.add(future);
        return this;
    }

    public FutureHelperResolver supplyAsync(ExSupplier supplier) {
        ExecutorCompletableFuture future = ExecutorCompletableFuture.supplyAsync(supplier, this.executor);
        this.futures.add(future);
        return this;
    }

    public FutureHelperResolver completedFuture(String name, Object value) {
        ExecutorCompletableFuture future = ExecutorCompletableFuture.completedFuture(value, this.executor);
        this.futureMap.put(name, future);
        this.futures.add(future);
        return this;
    }

    public FutureHelperResolver completedFuture(Object value) {
        this.futures.add(ExecutorCompletableFuture.completedFuture(value, this.executor));
        return this;
    }

    public FutureHelperResolver runAsync(ExRunnable r) {
        ExecutorCompletableFuture future = ExecutorCompletableFuture.runAsync(r, this.executor);
        this.futures.add(future);
        return this;
    }

    public void join() {
        if (this.futures.size() > 0) {
            ExecutorCompletableFuture.allOf(this.executor, (ExecutorCompletableFuture[])this.futures.toArray(new ExecutorCompletableFuture[0])).join();
        }

    }

    public ExecutorCompletableFuture<Void> allOf() {
        if (this.futures.size() > 0) {
            ExecutorCompletableFuture.allOf(this.executor, (ExecutorCompletableFuture[])this.futures.toArray(new ExecutorCompletableFuture[0]));
        }

        return null;
    }

    public <T> ExecutorCompletableFuture<T> anyOf() {
        return this.futures.size() > 0 ? ExecutorCompletableFuture.anyOf(this.executor, (ExecutorCompletableFuture[])this.futures.toArray(new ExecutorCompletableFuture[0])) : null;
    }

    public ExecutorCompletableFuture getFuture(String name) {
        return (ExecutorCompletableFuture)this.futureMap.get(name);
    }

    public <T> T get(String name) throws ExecutionException, InterruptedException {
        ExecutorCompletableFuture future = this.getFuture(name);
        return future != null ? (T) future.get() : null;
    }

    public <T> T get(String name, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorCompletableFuture future = this.getFuture(name);
        return future != null ? (T) future.get(timeout, unit) : null;
    }

    public <T> List<T> get() throws ExecutionException, InterruptedException {
        List<T> list = new ArrayList();
        Iterator var2 = this.futures.iterator();

        while(var2.hasNext()) {
            ExecutorCompletableFuture future = (ExecutorCompletableFuture)var2.next();
            list.add((T) future.get());
        }

        return list;
    }

    public <T> List<T> get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        List<T> list = new ArrayList();
        Iterator var5 = this.futures.iterator();

        while(var5.hasNext()) {
            ExecutorCompletableFuture future = (ExecutorCompletableFuture)var5.next();
            list.add((T) future.get(timeout, unit));
        }

        return list;
    }
}
