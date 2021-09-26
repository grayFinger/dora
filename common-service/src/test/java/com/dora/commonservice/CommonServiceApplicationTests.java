package com.dora.commonservice;

import com.dora.commonservice.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommonServiceApplicationTests {

    @Autowired
    private RedisService redisService;

    @Test
    void contextLoads() {
        String key = "test-k";
        redisService.put(key, 1, 30);

        System.out.println(redisService.get(key));
        System.out.println("=============");

    }

}
