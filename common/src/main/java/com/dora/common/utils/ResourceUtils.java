package com.dora.common.utils;


import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dora.common.exception.BusinessException;


import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

public class ResourceUtils {
    public ResourceUtils() {
    }

    public static String fromFile(File file) {
        try {
            return fromInputStream(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException var2) {
            throw new BusinessException("8000", "请求资源不存在");
        }
    }

    public static String fromClassResource(String file) {
        ClassPathResource classPathResource = new ClassPathResource(file);
        if (classPathResource.exists()) {
            try {
                return fromInputStream(classPathResource.getInputStream());
            } catch (Exception var3) {
                throw new BusinessException(var3);
            }
        } else {
            throw new BusinessException("8000", "请求资源不存在");
        }
    }

    public static String fromInputStream(InputStream is) {
        String var1;
        try {
            var1 = IOUtils.toString(is, "UTF-8");
        } catch (IOException var5) {
            throw new BusinessException("8000", "读取资源失败");
        } finally {
            IOUtils.closeQuietly(new Closeable[]{is, null});
        }

        return var1;
    }
}
