package com.dora.common.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ArrayUtils {
    public ArrayUtils() {
    }

    public static <T> T[] getArray(T[] array, T... values) {
        T[] tmps = array;
        if (array == null) {
            tmps = values;
        } else if (values != null) {
            tmps = Arrays.copyOf(array, array.length + values.length);
            System.arraycopy(values, 0, tmps, array.length, values.length);
        }

        return tmps;
    }

    public static <T> T[] getArray(List<T> list, T... values) {
        T[] tmps = null;
        Object[] newTmps;
        if (list != null && list.size() > 0) {
            newTmps = (Object[])((Object[])Array.newInstance(list.get(0).getClass(), list.size()));
            tmps = (T[]) list.toArray(newTmps);
        }

        if (values != null && values.length > 0) {
            if (tmps == null) {
                tmps = values;
            } else {
                newTmps = Arrays.copyOf(tmps, tmps.length + values.length);
                System.arraycopy(values, 0, newTmps, tmps.length, values.length);
                tmps = (T[]) newTmps;
            }
        }

        return tmps;
    }
}
