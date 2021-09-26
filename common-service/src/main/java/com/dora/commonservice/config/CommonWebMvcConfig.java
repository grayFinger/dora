package com.dora.commonservice.config;

import com.dora.commonservice.resolver.CurrentUserResolver;
import com.dora.commonservice.resolver.PartBodyResolver;
import com.dora.commonservice.interceptor.PermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CommonWebMvcConfig implements WebMvcConfigurer {

    @Value("${security:false}")
    private boolean security;

    @Autowired
    private PermissionInterceptor permissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (security)
            registry.addInterceptor(permissionInterceptor).addPathPatterns("/api/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CurrentUserResolver());
        resolvers.add(new PartBodyResolver());
    }
}
