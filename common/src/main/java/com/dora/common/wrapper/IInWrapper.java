package com.dora.common.wrapper;

import org.springframework.http.HttpEntity;

public interface IInWrapper {
    Object unwrap(Object obj);

    void valid(HttpEntity entity, Object obj);
}
