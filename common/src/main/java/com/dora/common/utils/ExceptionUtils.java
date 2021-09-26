package com.dora.common.utils;


import com.dora.common.exception.ICodeException;
import com.dora.common.exception.wrap.ExceptionWrapper;

public class ExceptionUtils {
    private static ExceptionWrapper exceptionWrapper;

    public ExceptionUtils() {
    }

    public static Throwable getCause(Throwable t) {
        Throwable e;
        for(e = t; e != null; e = e.getCause()) {
            if (e.getCause() == null || e.equals(e.getCause())) {
                if (exceptionWrapper != null && exceptionWrapper.exists(e)) {
                    e = exceptionWrapper.process(e);
                }
                break;
            }

            if (exceptionWrapper != null && exceptionWrapper.exists(e)) {
                e = exceptionWrapper.process(e);
                break;
            }

            if (e instanceof ICodeException) {
                break;
            }
        }

        return e;
    }

    public static void setExceptionWrapper(ExceptionWrapper exceptionWrapper) {
        ExceptionUtils.exceptionWrapper = exceptionWrapper;
    }
}
