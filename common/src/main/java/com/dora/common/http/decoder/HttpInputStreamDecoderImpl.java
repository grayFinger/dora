package com.dora.common.http.decoder;

import java.io.IOException;
import java.io.InputStream;

import com.dora.common.http.IHttpDecoder;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class HttpInputStreamDecoderImpl implements IHttpDecoder<ResponseBody, InputStream> {
    public HttpInputStreamDecoderImpl() {
    }

    public InputStream execute(ResponseBody body) throws IOException {
        BufferedSource source = body.source();
        source.request(9223372036854775807L);
        Buffer buffer = source.getBuffer();
        return buffer.clone().inputStream();
    }
}
