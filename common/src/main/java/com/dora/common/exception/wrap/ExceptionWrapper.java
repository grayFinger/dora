package com.dora.common.exception.wrap;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionWrapper {
    private static Logger logger = LoggerFactory.getLogger(ExceptionWrapper.class);
    private static Map<String, IExceptionWrapper> exceptions = new HashMap();
    private IExceptionWrapper defaultWrapper;

    public ExceptionWrapper() {
    }

    public <T extends Throwable> void register(Class<T> clazz, IExceptionWrapper<T> wrapper) {
        if (clazz != null && wrapper != null) {
            exceptions.put(clazz.getName(), wrapper);
            logger.debug("注册异常[{}]特殊处理=====>{}", clazz.getName(), wrapper.getClass().getName());
        }

    }

    public <T extends Throwable> void unregister(Class<T> clazz) {
        if (clazz != null) {
            exceptions.remove(clazz.getName());
        }

    }

    public boolean exists(Throwable t) {
        return t != null ? exceptions.containsKey(t.getClass().getName()) : false;
    }

    public Throwable process(Throwable t) {
        if (t == null) {
            return null;
        } else {
            IExceptionWrapper wrapper = (IExceptionWrapper)exceptions.get(t.getClass().getName());
            if (wrapper == null) {
                wrapper = this.defaultWrapper;
            }

            return wrapper != null ? wrapper.process(t) : t;
        }
    }

    public void setDefaultWrapper(IExceptionWrapper defaultWrapper) {
        this.defaultWrapper = defaultWrapper;
    }
}
