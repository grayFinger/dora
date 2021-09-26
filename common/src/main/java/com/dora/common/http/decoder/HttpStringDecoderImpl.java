package com.dora.common.http.decoder;

import java.io.IOException;
import java.io.InputStream;

import com.dora.common.http.IHttpDecoder;
import org.apache.commons.io.IOUtils;

public class HttpStringDecoderImpl implements IHttpDecoder<InputStream, String> {
    private String charset = "UTF-8";

    public HttpStringDecoderImpl() {
    }

    public HttpStringDecoderImpl(String charset) {
        this.charset = charset;
    }

    public String execute(InputStream in) throws IOException {
        return IOUtils.toString(in, this.charset);
    }
}
