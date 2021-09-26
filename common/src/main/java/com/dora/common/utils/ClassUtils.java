package com.dora.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class ClassUtils {
    public ClassUtils() {
    }

    public static Class<?> getGenericClass(Class<?> clazz, int index) {
        Type type = clazz.getGenericSuperclass();
        Class<?> genericClass = getGenericClass(type, index);
        if (genericClass == null) {
            Type[] types = clazz.getGenericInterfaces();
            Type[] var5 = types;
            int var6 = types.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Type t = var5[var7];
                if (t instanceof ParameterizedType) {
                    genericClass = getGenericClass(t, index);
                    if (genericClass != null) {
                        break;
                    }
                }
            }
        }

        if (genericClass == null && !clazz.getSuperclass().equals(Object.class)) {
            genericClass = getGenericClass(clazz.getSuperclass(), index);
        }

        return genericClass;
    }

    private static Class<?> getGenericClass(Type type, int index) {
        return type != null && type instanceof ParameterizedType ? (Class)((ParameterizedType)type).getActualTypeArguments()[index] : null;
    }

    public static Class<?> getGenericClass(Class<?> clazz) {
        return getGenericClass((Class)clazz, 0);
    }

    public static Set<Class> findClass(String path, Class<? extends Annotation>... annotioanTypes) throws Exception {
        Set<Class> classes = new LinkedHashSet();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        String pattern = "classpath*:" + path;
        Resource[] resources = resourcePatternResolver.getResources(pattern);
        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        List<AnnotationTypeFilter> filters = new ArrayList();
        Class[] var8 = annotioanTypes;
        int var9 = annotioanTypes.length;

        int var10;
        for(var10 = 0; var10 < var9; ++var10) {
            Class<? extends Annotation> clazz = var8[var10];
            filters.add(new AnnotationTypeFilter(clazz, false));
        }

        Resource[] var16 = resources;
        var9 = resources.length;

        for(var10 = 0; var10 < var9; ++var10) {
            Resource resource = var16[var10];
            if (resource.isReadable()) {
                MetadataReader reader = readerFactory.getMetadataReader(resource);
                String className = reader.getClassMetadata().getClassName();
                Iterator var14 = filters.iterator();

                while(var14.hasNext()) {
                    AnnotationTypeFilter filter = (AnnotationTypeFilter)var14.next();
                    if (filter.match(reader, readerFactory)) {
                        classes.add(Class.forName(className));
                    }
                }
            }
        }

        return classes;
    }
}
