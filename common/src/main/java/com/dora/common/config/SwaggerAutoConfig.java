package com.dora.common.config;

import com.dora.common.swagger.ISwaggerParameterResolver;
import com.google.common.collect.Lists;
import com.mg.swagger.framework.configuration.EnableSwaggerMgUi;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableSwaggerMgUi
@EnableConfigurationProperties({SwaggerConfig.class})
public class SwaggerAutoConfig implements WebMvcConfigurer {
    @Autowired(
        required = false
    )
    private ISwaggerParameterResolver swaggerParameterResolver;

    public SwaggerAutoConfig() {
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"swagger-ui.html"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/"});
        registry.addResourceHandler(new String[]{"document.html"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/"});
        registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"});
    }

    @Bean
    public Docket createRestApi(SwaggerConfig swagger) {
        List<Parameter> pars = new ArrayList();
        if (this.swaggerParameterResolver != null) {
            this.swaggerParameterResolver.addParameter(pars);
        }

        return (new Docket(DocumentationType.SWAGGER_2)).apiInfo(this.apiInfo(swagger)).select().apis(RequestHandlerSelectors.basePackage(swagger.getBasePackage())).paths(PathSelectors.any()).build().securityContexts(this.securityContexts()).securitySchemes(this.securitySchemes()).globalOperationParameters(pars);
    }

    private ApiInfo apiInfo(SwaggerConfig swagger) {
        return (new ApiInfoBuilder()).title(swagger.getTitle()).description(swagger.getDescription()).version(swagger.getVersion()).build();
    }

    private List<SecurityContext> securityContexts() {
        return Lists.newArrayList(new SecurityContext[]{SecurityContext.builder().securityReferences(this.defaultAuth()).forPaths(PathSelectors.regex("^(?!auth).*$")).build()});
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Lists.newArrayList(new SecurityReference[]{new SecurityReference("Authorization", authorizationScopes)});
    }

    private List<ApiKey> securitySchemes() {
        return Lists.newArrayList(new ApiKey[]{new ApiKey("Authorization", "token", "header")});
    }
}
