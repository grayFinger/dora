package com.dora.common.view;

import com.dora.common.utils.ResourceUtils;

import java.io.File;
import java.io.InputStream;

public class JsonFile {
    private String data;

    private JsonFile(String data) {
        this.data = data;
    }

    public static JsonFile fromFile(File file) {
        return new JsonFile(ResourceUtils.fromFile(file));
    }

    public static JsonFile fromClassResource(String file) {
        return new JsonFile(ResourceUtils.fromClassResource(file));
    }

    public static JsonFile fromInputStream(InputStream is) {
        return new JsonFile(ResourceUtils.fromInputStream(is));
    }

    public String getData() {
        return this.data;
    }
}
