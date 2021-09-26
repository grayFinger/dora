package com.dora.common.http;

import java.io.IOException;

@FunctionalInterface
public interface IHttpDecoder<I, T> {
    T execute(I body) throws IOException;
}
