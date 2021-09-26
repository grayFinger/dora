package com.dora.common.config;


import com.dora.common.convert.DateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConverterAutoConfig implements WebMvcConfigurer {
    public ConverterAutoConfig() {
    }

    @Bean
    public DateConverter dateConverter() {
        return new DateConverter();
    }

    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(this.dateConverter());
    }
}
