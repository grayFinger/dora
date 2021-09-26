package com.zhao.dorazuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
//import com.zhao.commonservice.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: ErrorFilter
 * @Author: zhaolianqi
 * @Date: 2021/8/25 14:19
 * @Version: v1.0
 */
@Component
public class ErrorFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
//        logger.error("Server error, from ip: {}", CommonUtils.getIpAddr(request));
//        requestContext.setSendZuulResponse(false);
//        requestContext.setResponseBody(BaseResponse.ERROR("Server error").toString());
        requestContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        requestContext.getResponse().setCharacterEncoding("UTF-8");
        requestContext.getResponse().setHeader("Content-Type", "application/json");
        requestContext.set("isSuccess", false);
        return null;
    }
}
