package com.deng.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.deng.order")
@MapperScan("com.deng.order.**.dao")
@SpringBootApplication(scanBasePackages = {"com.deng"})
public class DoraOrderAppliaction {
    public static void main(String[] args) {
        SpringApplication.run(DoraOrderAppliaction.class, args);
    }
}
