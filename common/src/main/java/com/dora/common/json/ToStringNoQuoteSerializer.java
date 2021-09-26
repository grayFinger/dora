package com.dora.common.json;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class ToStringNoQuoteSerializer implements ObjectSerializer {
    public ToStringNoQuoteSerializer() {
    }

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
        } else if (fieldName == null) {
            out.write(object.toString());
        } else {
            out.writeString(object.toString());
        }

    }
}
