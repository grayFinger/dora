package com.dora.common.execute;

import com.dora.common.exception.BusinessException;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class ExecutorCompletableFuture<T> implements Future<T> {
    private Executor executor;
    private CompletableFuture<T> future;

    public ExecutorCompletableFuture(CompletableFuture<T> future, Executor executor) {
        this.executor = executor;
        this.future = future;
    }

    public ExecutorCompletableFuture(CompletableFuture<T> future) {
        this.future = future;
    }

    public boolean isDone() {
        return this.future.isDone();
    }

    public static <U> ExecutorCompletableFuture<U> supplyAsync(ExSupplier<U> supplier) {
        return new ExecutorCompletableFuture(CompletableFuture.supplyAsync(() -> {
            try {
                return supplier.get();
            } catch (Exception var2) {
                throw new CompletionException(var2);
            }
        }));
    }

    public static <U> ExecutorCompletableFuture<U> supplyAsync(ExSupplier<U> supplier, Executor executor) {
        return executor == null ? supplyAsync(supplier) : new ExecutorCompletableFuture(CompletableFuture.supplyAsync(() -> {
            try {
                return supplier.get();
            } catch (Exception var2) {
                throw new CompletionException(var2);
            }
        }, executor), executor);
    }

    public static ExecutorCompletableFuture<Void> runAsync(ExRunnable runnable) {
        return new ExecutorCompletableFuture(CompletableFuture.runAsync(() -> {
            try {
                runnable.run();
            } catch (Exception var2) {
                throw new CompletionException(var2);
            }
        }));
    }

    public static ExecutorCompletableFuture<Void> runAsync(ExRunnable runnable, Executor executor) {
        return executor == null ? runAsync(runnable) : new ExecutorCompletableFuture(CompletableFuture.runAsync(() -> {
            try {
                runnable.run();
            } catch (Exception var2) {
                throw new CompletionException(var2);
            }
        }, executor), executor);
    }

    public static <U> ExecutorCompletableFuture<U> completedFuture(U value) {
        return new ExecutorCompletableFuture(CompletableFuture.completedFuture(value));
    }

    public static <U> ExecutorCompletableFuture<U> completedFuture(U value, Executor executor) {
        return new ExecutorCompletableFuture(CompletableFuture.completedFuture(value), executor);
    }

    public static ExecutorCompletableFuture<Void> allOf(ExecutorCompletableFuture<?>... cfs) {
        return new ExecutorCompletableFuture(CompletableFuture.allOf((CompletableFuture[])Arrays.stream(cfs).map((future) -> {
            return future.getFuture();
        }).toArray((x$0) -> {
            return new CompletableFuture[x$0];
        })));
    }

    public static ExecutorCompletableFuture<Void> allOf(Executor executor, ExecutorCompletableFuture<?>... cfs) {
        return new ExecutorCompletableFuture(CompletableFuture.allOf((CompletableFuture[])Arrays.stream(cfs).map((future) -> {
            return future.getFuture();
        }).toArray((x$0) -> {
            return new CompletableFuture[x$0];
        })), executor);
    }

    public static <U> ExecutorCompletableFuture<U> anyOf(ExecutorCompletableFuture<?>... cfs) {
        return new ExecutorCompletableFuture(CompletableFuture.anyOf((CompletableFuture[])Arrays.stream(cfs).map((future) -> {
            return future.getFuture();
        }).toArray((x$0) -> {
            return new CompletableFuture[x$0];
        })));
    }

    public static <U> ExecutorCompletableFuture<U> anyOf(Executor executor, ExecutorCompletableFuture<?>... cfs) {
        return new ExecutorCompletableFuture(CompletableFuture.anyOf((CompletableFuture[])Arrays.stream(cfs).map((future) -> {
            return future.getFuture();
        }).toArray((x$0) -> {
            return new CompletableFuture[x$0];
        })), executor);
    }

    public T get() throws InterruptedException, ExecutionException {
        try {
            return this.future.get();
        } catch (ExecutionException | CompletionException var3) {
            Throwable t = var3.getCause();
            if (t instanceof BusinessException) {
                throw (BusinessException)t;
            } else {
                throw new BusinessException(t);
            }
        } catch (BusinessException | InterruptedException var4) {
            throw var4;
        } catch (Exception var5) {
            throw new BusinessException(var5);
        }
    }

    public T join() {
        try {
            return this.future.join();
        } catch (CompletionException var3) {
            Throwable t = var3.getCause();
            if (t instanceof BusinessException) {
                throw (BusinessException)t;
            } else {
                throw new BusinessException(t);
            }
        } catch (BusinessException var4) {
            throw var4;
        } catch (Exception var5) {
            throw new BusinessException(var5);
        }
    }

    public T getNow(T valueIfAbsent) {
        return this.future.getNow(valueIfAbsent);
    }

    public boolean complete(T value) {
        return this.future.complete(value);
    }

    public boolean completeExceptionally(Throwable ex) {
        return this.future.completeExceptionally(ex);
    }

    public <U> ExecutorCompletableFuture<U> thenApply(Function<? super T, ? extends U> fn) {
        return new ExecutorCompletableFuture(this.future.thenApply(fn), this.executor);
    }

    public <U> ExecutorCompletableFuture<U> thenApplyAsync(Function<? super T, ? extends U> fn) {
        return this.executor == null ? new ExecutorCompletableFuture(this.future.thenApplyAsync(fn)) : new ExecutorCompletableFuture(this.future.thenApplyAsync(fn, this.executor), this.executor);
    }

    public ExecutorCompletableFuture<Void> thenAccept(Consumer<? super T> action) {
        return new ExecutorCompletableFuture(this.future.thenAccept(action), this.executor);
    }

    public ExecutorCompletableFuture<Void> thenAcceptAsync(Consumer<? super T> action) {
        return this.executor == null ? new ExecutorCompletableFuture(this.future.thenAcceptAsync(action)) : new ExecutorCompletableFuture(this.future.thenAcceptAsync(action, this.executor), this.executor);
    }

    public ExecutorCompletableFuture<Void> thenRun(ExRunnable action) {
        return new ExecutorCompletableFuture(this.future.thenRun(() -> {
            try {
                action.run();
            } catch (Exception var2) {
                throw new CompletionException(var2);
            }
        }), this.executor);
    }

    public ExecutorCompletableFuture<Void> thenRunAsync(ExRunnable action) {
        return this.executor == null ? new ExecutorCompletableFuture(this.future.thenRunAsync(() -> {
            try {
                action.run();
            } catch (Exception var2) {
                throw new CompletionException(var2);
            }
        })) : new ExecutorCompletableFuture(this.future.thenRunAsync(() -> {
            try {
                action.run();
            } catch (Exception var2) {
                throw new CompletionException(var2);
            }
        }, this.executor), this.executor);
    }

    public <U, V> ExecutorCompletableFuture<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return new ExecutorCompletableFuture(this.future.thenCombine(other, fn), this.executor);
    }

    public <U, V> ExecutorCompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return this.executor == null ? new ExecutorCompletableFuture(this.future.thenCombineAsync(other, fn)) : new ExecutorCompletableFuture(this.future.thenCombineAsync(other, fn, this.executor));
    }

    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        try {
            return this.future.get(timeout, unit);
        } catch (ExecutionException | CompletionException var6) {
            Throwable t = var6.getCause();
            if (t instanceof BusinessException) {
                throw (BusinessException)t;
            } else {
                throw new BusinessException(t);
            }
        } catch (BusinessException | TimeoutException | InterruptedException var7) {
            throw var7;
        } catch (Exception var8) {
            throw new BusinessException(var8);
        }
    }

    private <T, U extends Exception>BiConsumer<? super T, ? super Throwable> getBitConsumer(ExBiConsumer<T>  action) {
        return (value1, e1) -> {
            if (e1 == null) {
                action.accept( value1);
            } else if (!(e1 instanceof CompletionException) && !(e1 instanceof ExecutionException)) {
                if (e1 instanceof BusinessException) {
                    throw (BusinessException)e1;
                } else {
                    throw new BusinessException((Throwable) e1);
                }
            } else {
                Throwable t = e1.getCause();
                if (t instanceof BusinessException) {
                    throw (BusinessException)t;
                } else {
                    throw new BusinessException(t);
                }
            }
        };
    }

    public <U> ExecutorCompletableFuture<Void> thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return new ExecutorCompletableFuture(this.future.thenAcceptBoth(other, action), this.executor);
    }

    public <U> ExecutorCompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return this.executor == null ? new ExecutorCompletableFuture(this.future.thenAcceptBothAsync(other, action)) : new ExecutorCompletableFuture(this.future.thenAcceptBothAsync(other, action, this.executor));
    }

    public ExecutorCompletableFuture<Void> runAfterBoth(CompletionStage<?> other, Runnable action) {
        return new ExecutorCompletableFuture(this.future.runAfterBoth(other, action), this.executor);
    }

    public ExecutorCompletableFuture<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action) {
        return this.executor == null ? new ExecutorCompletableFuture(this.future.runAfterBothAsync(other, action)) : new ExecutorCompletableFuture(this.future.runAfterBothAsync(other, action, this.executor));
    }

    public <U> ExecutorCompletableFuture<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return new ExecutorCompletableFuture(this.future.applyToEither(other, fn), this.executor);
    }

    public <U> ExecutorCompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return this.executor == null ? new ExecutorCompletableFuture(this.future.applyToEitherAsync(other, fn)) : new ExecutorCompletableFuture(this.future.applyToEitherAsync(other, fn, this.executor));
    }

    public ExecutorCompletableFuture<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return new ExecutorCompletableFuture(this.future.acceptEither(other, action), this.executor);
    }

    public ExecutorCompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return this.executor == null ? new ExecutorCompletableFuture(this.future.acceptEitherAsync(other, action)) : new ExecutorCompletableFuture(this.future.acceptEitherAsync(other, action, this.executor));
    }

    public ExecutorCompletableFuture<Void> runAfterEither(CompletionStage<?> other, Runnable action) {
        return new ExecutorCompletableFuture(this.future.runAfterEither(other, action), this.executor);
    }

    public ExecutorCompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action) {
        return this.executor == null ? new ExecutorCompletableFuture(this.future.runAfterEitherAsync(other, action)) : new ExecutorCompletableFuture(this.future.runAfterEitherAsync(other, action, this.executor));
    }

    public <U> ExecutorCompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn) {
        return new ExecutorCompletableFuture(this.future.thenCompose(fn), this.executor);
    }

    public <U> ExecutorCompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) {
        return this.executor == null ? new ExecutorCompletableFuture(this.future.thenComposeAsync(fn)) : new ExecutorCompletableFuture(this.future.thenComposeAsync(fn, this.executor));
    }

    public ExecutorCompletableFuture<T> whenComplete(ExBiConsumer<? super T> action) {
        return new ExecutorCompletableFuture(this.future.whenComplete(this.getBitConsumer(action)), this.executor);
    }

    public ExecutorCompletableFuture<T> whenCompleteAsync(ExBiConsumer<? super T> action) {
        return this.executor == null ? new ExecutorCompletableFuture(this.future.whenCompleteAsync(this.getBitConsumer(action))) : new ExecutorCompletableFuture(this.future.whenCompleteAsync(this.getBitConsumer(action), this.executor));
    }

    public <U> ExecutorCompletableFuture<U> handle(BiFunction<? super T, Throwable, ? extends U> fn) {
        return new ExecutorCompletableFuture(this.future.handle(fn), this.executor);
    }

    public <U> ExecutorCompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn) {
        return this.executor == null ? new ExecutorCompletableFuture(this.future.handleAsync(fn)) : new ExecutorCompletableFuture(this.future.handleAsync(fn, this.executor));
    }

    public ExecutorCompletableFuture<T> toCompletableFuture() {
        return new ExecutorCompletableFuture(this.future.toCompletableFuture(), this.executor);
    }

    public ExecutorCompletableFuture<T> exceptionally(Function<Throwable, ? extends T> fn) {
        return new ExecutorCompletableFuture(this.future.exceptionally(fn), this.executor);
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.future.cancel(mayInterruptIfRunning);
    }

    public boolean isCancelled() {
        return this.future.isCancelled();
    }

    public boolean isCompletedExceptionally() {
        return this.future.isCompletedExceptionally();
    }

    public void obtrudeValue(T value) {
        this.future.obtrudeValue(value);
    }

    public void obtrudeException(Throwable ex) {
        this.future.obtrudeException(ex);
    }

    public int getNumberOfDependents() {
        return this.future.getNumberOfDependents();
    }

    public String toString() {
        return this.future.toString();
    }

    public CompletableFuture<T> getFuture() {
        return this.future;
    }
}
