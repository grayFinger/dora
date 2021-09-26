package com.dora.common.http.decoder;

import com.alibaba.fastjson.parser.Feature;
import com.dora.common.http.IHttpDecoder;
import com.dora.common.utils.JsonUtils;
import com.dora.common.utils.StringMap;
import com.dora.common.view.PageInfo;

import java.io.IOException;
import java.util.List;

public class HttpPageInfoDecoderImpl<T> implements IHttpDecoder<String, PageInfo<T>> {
    private Class<T> clazz;
    private String path;

    public HttpPageInfoDecoderImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    public HttpPageInfoDecoderImpl(Class<T> clazz, String path) {
        this.path = path;
        this.clazz = clazz;
    }

    public PageInfo<T> execute(String body) throws IOException {
        StringMap map = (StringMap) JsonUtils.fromJson(body, this.path, StringMap.class, new Feature[0]);
        Long total = map.getLong("total", 0L);
        Boolean firstPage = (Boolean)map.getValue("firstPage", Boolean.class, true);
        Boolean lastPage = (Boolean)map.getValue("lastPage", Boolean.class, true);
        List<T> rows = JsonUtils.fromJsonList(JsonUtils.toJson(map.getValue("rows")), this.clazz);
        return new PageInfo(rows, firstPage, lastPage, total);
    }
}
