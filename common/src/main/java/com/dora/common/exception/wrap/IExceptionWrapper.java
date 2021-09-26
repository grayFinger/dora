package com.dora.common.exception.wrap;

public interface IExceptionWrapper<T extends Throwable> {
    Throwable process(T t);
}
