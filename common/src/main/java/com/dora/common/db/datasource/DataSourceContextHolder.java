package com.dora.common.db.datasource;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceContextHolder {
    private static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);
    private static final ThreadLocal<String> contextHolder = new ThreadLocal();
    private static List<String> datasourceTypes = new ArrayList();
    private static String defaultDataSourceType;

    public DataSourceContextHolder() {
    }

    public static void setDefaultDataSourceType(String defaultDataSourceType) {
        DataSourceContextHolder.defaultDataSourceType = defaultDataSourceType;
    }

    public static void setDatasourceTypes(List<String> datasourceTypes) {
        DataSourceContextHolder.datasourceTypes = datasourceTypes;
    }

    public static void setDatasourceType(String datasourceType) {
        contextHolder.set(datasourceType);
    }

    public static String getDatasourceType() {
        String type = (String)contextHolder.get();
        if (type == null || !datasourceTypes.contains(type)) {
            type = defaultDataSourceType;
        }

        if (type == null && datasourceTypes.size() > 0) {
            type = (String)datasourceTypes.get(0);
        }

        return type;
    }

    public static void clearDatasourceType() {
        contextHolder.remove();
    }
}
