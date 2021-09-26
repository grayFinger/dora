package com.zhao.dorazuul.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: AccessLimitFilter
 * @Author: zhaolianqi
 * @Date: 2021/8/25 14:35
 * @Version: v1.0
 */
@Component
public class AccessLimitFilter extends ZuulFilter {

    private static final RateLimiter RATE_LIMITER = RateLimiter.create(1);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
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
        if (!RATE_LIMITER.tryAcquire(1, 0, TimeUnit.SECONDS)){
            RequestContext requestContext = RequestContext.getCurrentContext();
            requestContext.setSendZuulResponse(false);
           // requestContext.setResponseBody(BaseResponse.ERROR(HttpStatus.TOO_MANY_REQUESTS.value(),"访问受限").toString());
            requestContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
            requestContext.getResponse().setCharacterEncoding("UTF-8");
            requestContext.getResponse().setHeader("Content-Type", "application/json");
        }
        return null;
    }
}
