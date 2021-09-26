package com.dora.common.hystrix;

import com.dora.common.utils.SpringContextUtils;
import com.netflix.hystrix.strategy.properties.HystrixDynamicProperties;
import com.netflix.hystrix.strategy.properties.HystrixDynamicProperty;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.core.env.Environment;

public class HystrixYmlDynamicProperties implements HystrixDynamicProperties {
    private Environment environment;

    public HystrixYmlDynamicProperties() {
    }

    public HystrixDynamicProperty<String> getString(String name, String defaultValue) {
        return new HystrixProperty(name, (value) -> {
            return value != null && value.length() != 0 ? value : defaultValue;
        });
    }

    public HystrixDynamicProperty<Integer> getInteger(String name, Integer defaultValue) {
        return new HystrixProperty(name, (value) -> {
            return value != null && value.length() != 0 ? NumberUtils.toInt(value, defaultValue) : defaultValue;
        });
    }

    public HystrixDynamicProperty<Long> getLong(String name, Long defaultValue) {
        return new HystrixProperty(name, (value) -> {
            return value != null && value.length() != 0 ? NumberUtils.toLong(value, defaultValue) : defaultValue;
        });
    }

    public HystrixDynamicProperty<Boolean> getBoolean(String name, Boolean defaultValue) {
        return new HystrixProperty(name, (value) -> {
            return value != null && value.length() != 0 ? BooleanUtils.toBoolean(value) : defaultValue;
        });
    }

    private Environment getEnvironment() {
        if (this.environment == null) {
            this.environment = (Environment) SpringContextUtils.getBean(Environment.class);
        }

        return this.environment;
    }

    @FunctionalInterface
    private interface IPropertyCallback<T> {
        T get(String value);
    }

    private class HystrixProperty<T> implements HystrixDynamicProperty<T> {
        private String name;
        private IPropertyCallback<T> callback;

        public HystrixProperty(String name, IPropertyCallback<T> callback) {
            this.name = name;
            this.callback = callback;
        }

        public String getName() {
            return this.name;
        }

        public void addCallback(Runnable runnable) {
        }

        public T get() {
            return this.callback.get(HystrixYmlDynamicProperties.this.getEnvironment().getProperty(this.name));
        }
    }
}
