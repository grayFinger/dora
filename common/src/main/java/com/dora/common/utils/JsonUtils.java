package com.dora.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

public class JsonUtils {
    public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static boolean PRETTY_FORMAT = false;
    private static List<SerializeFilter> filters = new ArrayList();
    private static SerializeConfig config;
    private static List<SerializerFeature> toJsonFeatures;
    private static List<Feature> fromJsonFeatures;

    public JsonUtils() {
    }

    public static String toJson(Object obj) {
        return toJson(obj, DEFAULT_DATE_FORMAT);
    }

    public static String toJson(Object obj, SerializeFilter[] filters, String dateFormat, SerializerFeature... features) {
        if (obj == null) {
            return null;
        } else if (obj instanceof String) {
            return (String)obj;
        } else {
            filters = (SerializeFilter[])ArrayUtils.getArray(JsonUtils.filters, filters);
            if (filters == null) {
                filters = new SerializeFilter[0];
            }

            features = (SerializerFeature[])ArrayUtils.getArray(toJsonFeatures, features);
            if (features == null) {
                features = new SerializerFeature[0];
            }

            if (PRETTY_FORMAT) {
                features = (SerializerFeature[])ArrayUtils.getArray(features, new SerializerFeature[]{SerializerFeature.PrettyFormat});
            }

            if (config == null) {
                config = SerializeConfig.globalInstance;
            }

            return JSON.toJSONString(obj, config, filters, dateFormat, JSON.DEFAULT_GENERATE_FEATURE, features);
        }
    }

    public static String toJson(Object obj, String dateFormat) {
        if (obj == null) {
            return null;
        } else {
            return obj instanceof String ? (String)obj : toJson(obj, (SerializeFilter[])null, dateFormat);
        }
    }

    public static <T> List<T> fromJsonList(String value, Class<T> clazz) {
        return JSON.parseArray(value, clazz);
    }

    public static <T> List<T> fromJsonList(String value, String path, Class<T> clazz) {
        if (path == null) {
            return fromJsonList(value, clazz);
        } else {
            Object pathValue = JSONPath.read(value, path);
            return pathValue == null ? null : fromJsonList(pathValue.toString(), clazz);
        }
    }

    public static <T> T fromJson(String value, Class<T> clazz, Feature... features) {
        if (String.class.isAssignableFrom(clazz)) {
            return (T) value;
        } else {
            features = (Feature[])ArrayUtils.getArray(fromJsonFeatures, features);
            if (features == null) {
                features = new Feature[0];
            }
            return JSON.parseObject(value, clazz, features);
        }
    }

    public static <T> T fromJson(String value, TypeReference<T> type, Feature... features) {
        if (type.equals(String.class)) {
            return (T) value;
        } else {
            features = (Feature[])ArrayUtils.getArray(fromJsonFeatures, features);
            if (features == null) {
                features = new Feature[0];
            }

            return JSON.parseObject(value, type, features);
        }
    }

    public static <T> T fromJson(String value, String path, Class<T> clazz, Feature... features) {
        if (path == null) {
            return fromJson(value, clazz);
        } else {
            Object pathValue = JSONPath.read(value, path);
            return pathValue == null ? null : fromJson(pathValue.toString(), clazz, features);
        }
    }

    public static <T> T fromJsonWidthFile(String filePath, Class<T> clazz, Feature... features) throws IOException {
        Resource resource = new ClassPathResource(filePath);
        return String.class.isAssignableFrom(clazz) ? (T) StreamUtils.copyToString(resource.getInputStream(), Charset.forName("UTF-8")) : JSON.parseObject(resource.getInputStream(), Charset.forName("UTF-8"), clazz, (Feature[])ArrayUtils.getArray(fromJsonFeatures, features));
    }

    public static <T> List<T> fromJsonListWithFile(String filePath, Class<T> clazz, Feature... features) throws IOException {
        Resource resource = new ClassPathResource(filePath);
        return (List)JSON.parseObject(resource.getInputStream(), Charset.forName("UTF-8"), (new TypeReference<List<T>>() {
        }).getType(), (Feature[])ArrayUtils.getArray(fromJsonFeatures, features));
    }

    public static List<SerializeFilter> getFilters() {
        return filters;
    }

    public static void addFilter(SerializeFilter filter) {
        if (filters == null) {
            filters = new ArrayList();
        }

        filters.add(filter);
    }

    public static void setFilters(List<SerializeFilter> filters) {
        JsonUtils.filters = filters;
    }

    public static SerializeConfig getConfig() {
        return config;
    }

    public static void setConfig(SerializeConfig config) {
        JsonUtils.config = config;
    }

    public static List<SerializerFeature> getToJsonFeatures() {
        return toJsonFeatures;
    }

    public static void addToJsonFeature(SerializerFeature feature) {
        if (toJsonFeatures == null) {
            toJsonFeatures = new ArrayList();
        }

        toJsonFeatures.add(feature);
    }

    public static void setToJsonFeatures(List<SerializerFeature> toJsonFeatures) {
        JsonUtils.toJsonFeatures = toJsonFeatures;
    }

    public static List<Feature> getFromJsonFeatures() {
        return fromJsonFeatures;
    }

    public static void addFromJsonFeature(Feature feature) {
        if (fromJsonFeatures == null) {
            fromJsonFeatures = new ArrayList();
        }

        fromJsonFeatures.add(feature);
    }

    public static void setFromJsonFeatures(List<Feature> fromJsonFeatures) {
        JsonUtils.fromJsonFeatures = fromJsonFeatures;
    }

    static {
        config = SerializeConfig.globalInstance;
        toJsonFeatures = new ArrayList();
        fromJsonFeatures = new ArrayList();
    }
}
