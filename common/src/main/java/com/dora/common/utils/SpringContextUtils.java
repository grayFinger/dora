package com.dora.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public SpringContextUtils() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext != null) {
            try {
                return applicationContext.getBean(clazz);
            } catch (Throwable var2) {
            }
        }

        return null;
    }

    public static <T> T getBean(String name) {
        if (applicationContext != null) {
            try {
                return (T) applicationContext.getBean(name);
            } catch (Throwable var2) {
            }
        }

        return null;
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        if (applicationContext != null) {
            try {
                return applicationContext.getBean(name, clazz);
            } catch (Throwable var3) {
            }
        }

        return null;
    }

    public static <T> T register(String name, Class<T> beanClass) {
        if (applicationContext != null && applicationContext instanceof ConfigurableApplicationContext) {
            T obj = getBean(name, beanClass);
            if (obj == null) {
                ConfigurableApplicationContext context = (ConfigurableApplicationContext)applicationContext;
                DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)context.getBeanFactory();
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(beanClass);
                beanFactory.registerBeanDefinition(name, beanDefinitionBuilder.getBeanDefinition());
                return getBean(name, beanClass);
            } else {
                return obj;
            }
        } else {
            return null;
        }
    }

    public static <T> T register(Class<T> beanClass) {
        T instance = getBean(beanClass);
        return instance == null ? register(beanClass.getName(), beanClass) : instance;
    }

    public static <T> T register(T bean) {
        return (T) register(bean.getClass());
    }

    public static <T> T register(String name, T bean) {
        return (T) register(name, bean.getClass());
    }
}
