package com.dora.common.wrapper.impl;

import com.dora.common.wrapper.IInWrapper;
import org.springframework.http.HttpEntity;

public class NoneInWrapper implements IInWrapper {
    public NoneInWrapper() {
    }

    public Object unwrap(Object obj) {
        return obj;
    }

    public void valid(HttpEntity entity, Object obj) {
    }
}
