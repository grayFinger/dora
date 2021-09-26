package com.dora.common.utils;


import java.util.Collection;
import java.util.Map;

import com.dora.common.exception.BusinessException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class AssertUtils {
    public AssertUtils() {
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException("7001", message);
        }
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "参数值应为True");
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new BusinessException("7001", message);
        }
    }

    public static void isMatch(String text, String pattern, String message) {
        if (!text.matches(pattern)) {
            throw new BusinessException("7001", message);
        }
    }

    public static void isNull(Object object) {
        isNull(object, "参数应该为空");
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException("7001", message);
        }
    }

    public static void notNull(Object object) {
        notNull(object, "参数不能为空");
    }

    public static void hasLength(String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new BusinessException("7001", message);
        }
    }

    public static void hasLength(String text) {
        hasLength(text, "参数不能为空");
    }

    public static void hasText(String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new BusinessException("7001", message);
        }
    }

    public static void hasText(String text) {
        hasText(text, "参数不能为空");
    }

    public static void doesNotContain(String textToSearch, String substring, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new BusinessException("7001", message);
        }
    }

    public static void doesNotContain(String textToSearch, String substring) {
        doesNotContain(textToSearch, substring, "参数必须包含子字符串 [" + substring + "]");
    }

    public static void notEmpty(Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BusinessException("7001", message);
        }
    }

    public static void notEmpty(Object[] array) {
        notEmpty(array, "参数至少包含一个元素");
    }

    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            Object[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (element == null) {
                    throw new BusinessException("7001", message);
                }
            }
        }

    }

    public static void noNullElements(Object[] array) {
        noNullElements(array, "参数不能包含空元素");
    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException("7001", message);
        }
    }

    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection, "参数至少包含一个元素");
    }

    public static void notEmpty(Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new BusinessException("7001", message);
        }
    }

    public static void notEmpty(Map<?, ?> map) {
        notEmpty(map, "参数至少包含一个元素");
    }

    public static void isInstanceOf(Class<?> clazz, Object obj) {
        isInstanceOf(clazz, obj, "");
    }

    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, "参数类型不能为空");
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException((StringUtils.hasLength(message) ? message + " " : "") + "Object of class [" + (obj != null ? obj.getClass().getName() : "null") + "] must be an instance of " + type);
        }
    }

    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException((StringUtils.hasLength(message) ? message + " " : "") + subType + " is not assignable to " + superType);
        }
    }

    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void state(boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }
}
