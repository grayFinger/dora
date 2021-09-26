package com.dora.common.db.config;

import javax.sql.DataSource;

import com.dora.common.db.datasource.EncryptHikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public class DataSourceConfig {
    public DataSourceConfig() {
    }

    @Bean(
            name = {"dataSource"}
    )
    @Primary
    @ConfigurationProperties(
            prefix = "spring.datasource"
    )
    public DataSource encryptDataSource() {
        return new EncryptHikariDataSource();
    }
}
