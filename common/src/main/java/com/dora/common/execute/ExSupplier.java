package com.dora.common.execute;

public interface ExSupplier<T> {
    T get() throws Exception;
}
