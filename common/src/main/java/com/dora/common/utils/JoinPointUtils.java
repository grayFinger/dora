package com.dora.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

public class JoinPointUtils {
    public JoinPointUtils() {
    }

    public static Method getMethod(JoinPoint jp) {
        if (jp == null) {
            return null;
        } else {
            Signature signature = jp.getSignature();
            if (signature instanceof MethodSignature) {
                MethodSignature methodSignature = (MethodSignature)signature;
                return methodSignature.getMethod();
            } else {
                return null;
            }
        }
    }

    public static <T extends Annotation> T getAnnotationByMethod(JoinPoint jp, Class<T> annoClazz) {
        Method method = getMethod(jp);
        return method == null ? null : AnnotationUtils.findAnnotation(method, annoClazz);
    }

    public static <T extends Annotation> T getAnnotationByClass(JoinPoint jp, Class<T> annoClazz) {
        Object target = jp.getTarget();
        return AnnotationUtils.findAnnotation(target.getClass(), annoClazz);
    }

    public static <T extends Annotation> T getAnnotation(JoinPoint jp, Class<T> annoClazz) {
        T anno = getAnnotationByMethod(jp, annoClazz);
        if (anno == null) {
            anno = getAnnotationByClass(jp, annoClazz);
        }

        return anno;
    }
}
