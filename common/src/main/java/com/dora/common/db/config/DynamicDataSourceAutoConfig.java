package com.dora.common.db.config;

import com.dora.common.db.aop.DynamicDataSourceAspect;
import com.dora.common.db.aop.DynamicTransactionAspect;
import com.dora.common.db.datasource.DataSourceContextHolder;
import com.dora.common.db.datasource.DynamicDataSource;
import com.dora.common.utils.StringMap;
import com.zaxxer.hikari.HikariDataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

public class DynamicDataSourceAutoConfig implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceAutoConfig.class);
    private Map<Object, DataSource> dataSourceMap = new HashMap();
    private String defaultDatasource;

    public DynamicDataSourceAutoConfig() {
    }


    @SneakyThrows
    public void setEnvironment(Environment environment) {
        try {
            StringMap map = (StringMap)Binder.get(environment).bind("spring.datasource", StringMap.class).orElse(new StringMap());
            this.defaultDatasource = (String)map.remove("default-name");
            Map<Object, Object> defaultMap = (Map)map.remove("default");
            Iterator var4 = map.keySet().iterator();

            while(var4.hasNext()) {
                String key = (String)var4.next();
                if (this.defaultDatasource == null) {
                    this.defaultDatasource = key;
                }

                Map<Object, Object> keyMap = (Map)map.get(key);
                String type = (String)keyMap.get("type");
                if (type == null) {
                    type = (String)defaultMap.get("type");
                }

                Class<? extends DataSource> datasourceType = this.getDataSourceType(type);
                this.bindDatasource(key, datasourceType, environment, defaultMap);
            }

            log.debug("使用多数据源：{}", new ArrayList(map.keySet()));
        } catch (Throwable var9) {
            throw var9;
        }
    }

    private void bindDatasource(String type, Class<? extends DataSource> datasourceType, Environment environment, Map<Object, Object> defaultMap) throws Throwable {
        Binder binder = this.createBinder(environment, defaultMap, type);
        HikariDataSource dataSource = (HikariDataSource)binder.bindOrCreate("spring.datasource." + type, datasourceType);
        this.dataSourceMap.put(type, dataSource);
    }

    private Binder createBinder(Environment environment, Map<Object, Object> defaultMap, String type) {
        if (defaultMap != null && defaultMap.size() != 0) {
            Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(environment);
            List<ConfigurationPropertySource> configurationPropertySources = new ArrayList();
            Iterator var6 = sources.iterator();

            while(var6.hasNext()) {
                ConfigurationPropertySource source = (ConfigurationPropertySource)var6.next();
                configurationPropertySources.add(source);
            }

            Map<String, Object> dataMap = new LinkedHashMap();
            List<String> types = new ArrayList();
            Iterator var8 = defaultMap.keySet().iterator();

            while(var8.hasNext()) {
                Object key = var8.next();
                types.add(String.valueOf(key));
                dataMap.put("spring.datasource." + type + "." + key, defaultMap.get(key));
            }

            DataSourceContextHolder.setDatasourceTypes(types);
            configurationPropertySources.add(new MapConfigurationPropertySource(dataMap));
            PropertySourcesPlaceholdersResolver placeholdersResolver = new PropertySourcesPlaceholdersResolver(environment);
            return new Binder(configurationPropertySources, placeholdersResolver);
        } else {
            return Binder.get(environment);
        }
    }

    private Class<? extends DataSource> getDataSourceType(String type) {
        Class<? extends DataSource> clazz = null;
        if (type != null && type.length() > 0) {
            try {
                clazz = (Class<? extends DataSource>) Class.forName(type);
            } catch (Exception var4) {
                log.error("加载数据源[]类失败", type, var4);
            }
        }

        if (clazz == null) {
            clazz = HikariDataSource.class;
        }

        return clazz;
    }

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        DataSource defaultDataSource = (DataSource)this.dataSourceMap.get(this.defaultDatasource);
        if (defaultDataSource != null) {
            mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
            DataSourceContextHolder.setDefaultDataSourceType(this.defaultDatasource);
        }

        mpv.addPropertyValue("targetDataSources", this.dataSourceMap);
        List<String> types = new ArrayList();
        Iterator var7 = this.dataSourceMap.keySet().iterator();

        while(var7.hasNext()) {
            Object key = var7.next();
            types.add(String.valueOf(key));
        }

        DataSourceContextHolder.setDatasourceTypes(types);
        registry.registerBeanDefinition("dataSource", beanDefinition);
        GenericBeanDefinition dynamicDataSourceAspectDef = new GenericBeanDefinition();
        dynamicDataSourceAspectDef.setBeanClass(DynamicDataSourceAspect.class);
        dynamicDataSourceAspectDef.setSynthetic(true);
        registry.registerBeanDefinition("dynamicDataSourceAspect", dynamicDataSourceAspectDef);
        GenericBeanDefinition dynamicTransactionAspectDef = new GenericBeanDefinition();
        dynamicTransactionAspectDef.setBeanClass(DynamicTransactionAspect.class);
        dynamicTransactionAspectDef.setSynthetic(true);
        registry.registerBeanDefinition("dynamicTransactionAspect", dynamicTransactionAspectDef);
    }
}
