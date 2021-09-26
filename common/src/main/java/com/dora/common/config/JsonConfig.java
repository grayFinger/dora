package com.dora.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dora.common.utils.JsonUtils;

import java.util.List;

public class JsonConfig {
    private String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private boolean prettyFormat = false;

    public JsonConfig() {
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        if (dateFormat != null) {
            JSON.DEFFAULT_DATE_FORMAT = dateFormat;
            JsonUtils.DEFAULT_DATE_FORMAT = dateFormat;
        }

    }

    public boolean isPrettyFormat() {
        return this.prettyFormat;
    }

    public void setPrettyFormat(boolean prettyFormat) {
        this.prettyFormat = prettyFormat;
        JsonUtils.PRETTY_FORMAT = prettyFormat;
    }

    public List<SerializeFilter> getFilters() {
        return JsonUtils.getFilters();
    }

    public void addFilter(SerializeFilter filter) {
        JsonUtils.addFilter(filter);
    }

    public void setFilters(List<SerializeFilter> filters) {
        JsonUtils.setFilters(filters);
    }

    public SerializeConfig getConfig() {
        return JsonUtils.getConfig();
    }

    public void setConfig(SerializeConfig config) {
        JsonUtils.setConfig(config);
    }

    public List<SerializerFeature> getToJsonFeatures() {
        return JsonUtils.getToJsonFeatures();
    }

    public void setToJsonFeatures(List<SerializerFeature> toJsonFeatures) {
        JsonUtils.setToJsonFeatures(toJsonFeatures);
    }

    public void addToJsonFeature(SerializerFeature feature) {
        JsonUtils.addToJsonFeature(feature);
    }

    public List<Feature> getFromJsonFeatures() {
        return JsonUtils.getFromJsonFeatures();
    }

    public void setFromJsonFeatures(List<Feature> fromJsonFeatures) {
        JsonUtils.setFromJsonFeatures(fromJsonFeatures);
    }

    public void addFromJsonFeature(Feature feature) {
        JsonUtils.addFromJsonFeature(feature);
    }
}
