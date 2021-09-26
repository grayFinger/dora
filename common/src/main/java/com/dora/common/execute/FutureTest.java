package com.dora.common.execute;

import com.dora.common.exception.BusinessException;

import java.util.UUID;

public class FutureTest {
    public FutureTest() {
    }

    private static String test() {
        try {
            Thread.sleep(5000L);
        } catch (Exception var1) {
            var1.printStackTrace();
        }

        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        throw new BusinessException("333", "come here");
    }

    public static void main(String[] args) throws Exception {
        FutureHelper futureHelper = new FutureHelper();
        FutureHelperResolver resolver = futureHelper.create();

        for(int i = 0; i < 20; ++i) {
            resolver.supplyAsync("test" + i, FutureTest::test);
        }

        resolver.join();
    }
}
