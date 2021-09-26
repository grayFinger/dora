package com.dora.common.utils;

import java.util.Random;
import org.apache.commons.lang.RandomStringUtils;

public class RandomUtils {
    public RandomUtils() {
    }

    public static long getLong() {
        Random random = new Random();
        return random.nextLong();
    }

    public static int getInteger() {
        Random random = new Random();
        return random.nextInt() + random.nextInt();
    }

    public static double getDouble() {
        Random random = new Random();
        return (double)random.nextInt() + random.nextDouble();
    }

    public static String getString(int num) {
        return RandomStringUtils.random(6, false, true);
    }
}
