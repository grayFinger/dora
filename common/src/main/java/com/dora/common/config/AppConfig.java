//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dora.common.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);
    @Value("${spring.application.name}")
    private String name;
    @Value("${server.port}")
    private Integer port;
    private String host;

    public AppConfig() {
        try {
            this.host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException var2) {
            logger.warn("获取服务端的IP地址失败");
        }

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getHost() {
        return this.host;
    }
}
