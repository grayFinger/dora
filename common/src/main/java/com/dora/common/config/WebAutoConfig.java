package com.dora.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

import com.dora.common.exception.wrap.BindExceptionWrapper;
import com.dora.common.exception.wrap.ConstraintViolationExceptionWrapper;
import com.dora.common.exception.wrap.ExceptionWrapper;
import com.dora.common.exception.wrap.MethodArgumentNotValidExceptionWrapper;
import com.dora.common.json.ResponseBodyWrapFactoryBean;
import com.dora.common.json.ToStringNoQuoteSerializer;
import com.dora.common.utils.ArrayUtils;
import com.dora.common.utils.JsonUtils;
import com.dora.common.utils.SpringContextUtils;
import com.dora.common.web.CommonFilter;

import com.dora.common.wrapper.IOutWrapper;
import com.dora.common.wrapper.OutWrapperService;
import com.dora.common.wrapper.impl.CommonOutWrapper;
import com.dora.common.wrapper.impl.HeaderOutWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebAutoConfig implements WebMvcConfigurer {
    public WebAutoConfig() {
    }

    @Bean
    @ConditionalOnMissingBean({IOutWrapper.class})
    @ConditionalOnProperty(
        name = {"spring.out.wrapper"},
        havingValue = "common"
    )
    public IOutWrapper commonOutWrapper() {
        return new CommonOutWrapper();
    }

    @Bean
    @ConfigurationProperties(
        prefix = "spring.request"
    )
    public RequestConfig requestConfig() {
        return new RequestConfig();
    }

    @Bean
    @ConditionalOnMissingBean({IOutWrapper.class})
    @ConditionalOnProperty(
        name = {"spring.out.wrapper"},
        havingValue = "header",
        matchIfMissing = true
    )
    public IOutWrapper headerOutWrapper() {
        return new HeaderOutWrapper();
    }

    @Bean
    @ConditionalOnMissingBean({OutWrapperService.class})
    @ConfigurationProperties(
        prefix = "spring.out.ignore"
    )
    public OutWrapperService outWrapperService(IOutWrapper outWrapper) {
        OutWrapperService outWrapperService = new OutWrapperService();
        outWrapperService.setOutWrapper(outWrapper);
        return outWrapperService;
    }

    @Bean
    public ResponseBodyWrapFactoryBean responseBodyWrapFactoryBean() {
        return new ResponseBodyWrapFactoryBean();
    }

    @Bean
    public SpringContextUtils springContextUtils() {
        return new SpringContextUtils();
    }

    @Bean
    @ConfigurationProperties("spring.json")
    public JsonConfig jsonConfig() {
        JsonConfig jsonConfig = new JsonConfig();
        JSON.DEFFAULT_DATE_FORMAT = jsonConfig.getDateFormat();
        return jsonConfig;
    }

    @Bean
    public FastJsonHttpMessageConverter jsonMessageConverter() {
        FastJsonHttpMessageConverter messageConverter = new FastJsonHttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList();
        mediaTypes.add(MediaType.valueOf("text/json;charset=UTF-8"));
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        messageConverter.setSupportedMediaTypes(mediaTypes);
        JsonConfig jsonConfig = this.jsonConfig();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Long.class, new ToStringSerializer());
        serializeConfig.put(String.class, new ToStringNoQuoteSerializer());
        fastJsonConfig.setSerializeConfig(serializeConfig);
        if (jsonConfig.isPrettyFormat()) {
            fastJsonConfig.setSerializerFeatures((SerializerFeature[]) ArrayUtils.getArray(JsonUtils.getToJsonFeatures(), new SerializerFeature[]{SerializerFeature.WriteDateUseDateFormat, SerializerFeature.PrettyFormat}));
        } else {
            fastJsonConfig.setSerializerFeatures((SerializerFeature[])ArrayUtils.getArray(JsonUtils.getToJsonFeatures(), new SerializerFeature[]{SerializerFeature.WriteDateUseDateFormat}));
        }

        SerializeFilter[] filters = (SerializeFilter[])ArrayUtils.getArray(jsonConfig.getFilters(), new SerializeFilter[0]);
        if (filters == null) {
            filters = new SerializeFilter[0];
        }

        fastJsonConfig.setSerializeFilters(filters);
        messageConverter.setFastJsonConfig(fastJsonConfig);
        return messageConverter;
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Iterator it = converters.iterator();

        while(it.hasNext()) {
            HttpMessageConverter<?> converter = (HttpMessageConverter)it.next();
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                it.remove();
            }
        }

        converters.add(this.jsonMessageConverter());
    }

    @Bean
    public MultipartProperties multipartProperties(MultipartProperties uploadConfig) {
        MultipartProperties multipartProperties = new MultipartProperties();
        multipartProperties.setEnabled(true);
        multipartProperties.setMaxFileSize(uploadConfig.getMaxFileSize());
        multipartProperties.setMaxRequestSize(uploadConfig.getMaxRequestSize());
        if (StringUtils.isNotEmpty(uploadConfig.getLocation())) {
            File file = new File(uploadConfig.getLocation());
            if (!file.exists()) {
                file.mkdirs();
            }

            multipartProperties.setLocation(uploadConfig.getLocation());
        }

        multipartProperties.setResolveLazily(true);
        return multipartProperties;
    }

    public void addInterceptors(InterceptorRegistry registry) {
    }

    @Bean
    public FilterRegistrationBean<CommonFilter> xssFilter() {
        RequestConfig requestConfig = this.requestConfig();
        requestConfig.getNoFilterReferers().add("**/swagger-ui.html");
        requestConfig.getNoFilterReferers().add("**/document.html");
        requestConfig.getNoFilterReferers().add("**/monitoring");
        requestConfig.getNoFilterUrls().add("**/error");
        requestConfig.getNoFilterUrls().add("**/monitoring");
        requestConfig.getNoFilterUrls().add("**/document.html");
        requestConfig.getNoFilterUrls().add("**/swagger-ui.html");
        CommonFilter commonFilter = new CommonFilter();
        commonFilter.setNoFilterReferers(requestConfig.getNoFilterReferers());
        commonFilter.setNoFilterUrls(requestConfig.getNoFilterUrls());
        FilterRegistrationBean<CommonFilter> bean = new FilterRegistrationBean();
        bean.setFilter(commonFilter);
        bean.setName("xssFilter");
        bean.addUrlPatterns(new String[]{"/*"});
        bean.setOrder(1);
        return bean;
    }

    @Resource
    private void configExceptionWrapper(ExceptionWrapper exceptionWrapper) {
        exceptionWrapper.register(BindException.class, new BindExceptionWrapper());
        exceptionWrapper.register(ConstraintViolationException.class, new ConstraintViolationExceptionWrapper());
        exceptionWrapper.register(MethodArgumentNotValidException.class, new MethodArgumentNotValidExceptionWrapper());
    }

    static {
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    }
}
