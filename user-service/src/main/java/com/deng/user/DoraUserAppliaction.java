package com.deng.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.deng.user")
@MapperScan("com.deng.user.**.dao")
@SpringBootApplication(scanBasePackages = {"com.deng"})
public class DoraUserAppliaction {
    public static void main(String[] args) {
        SpringApplication.run(DoraUserAppliaction.class, args);
    }
}
