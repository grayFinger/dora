package com.dora.common.exception.wrap;

public class DefaultExceptionWrapper implements IExceptionWrapper {
    public DefaultExceptionWrapper() {
    }

    public Throwable process(Throwable t) {
        return t;
    }
}
