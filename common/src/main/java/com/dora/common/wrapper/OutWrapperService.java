package com.dora.common.wrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dora.common.wrapper.impl.SwaggerOutWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;

public class OutWrapperService {
    private List<String> ignorePaths = new ArrayList();
    private Map<String, Boolean> pathMatchCache = new HashMap();
    private String path;
    private IOutWrapper swaggerOutWrapper = new SwaggerOutWrapper();
    private IOutWrapper outWrapper;

    public OutWrapperService() {
    }

    @PostConstruct
    public void init() {
        this.ignorePaths.add("/swagger*/**");
        this.ignorePaths.add("/v2/api-docs/**");
        this.ignorePaths.add("/webjars/**");
        this.ignorePaths.add("/actuator/**");
        this.ignorePaths.add("/hystrix/**");
        this.ignorePaths.add("/druid/**");
        this.ignorePaths.add("/hystrix.stream");
        if (StringUtils.isNotEmpty(this.path)) {
            this.path = this.path.trim();
            String[] paths = this.path.split(";", -1);
            String[] var2 = paths;
            int var3 = paths.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String p = var2[var4];
                if (StringUtils.isNotEmpty(p)) {
                    this.ignorePaths.add(p);
                }
            }
        }

    }

    public boolean isValid(String uri) {
        Boolean matched = (Boolean)this.pathMatchCache.get(uri);
        if (matched != null) {
            return matched;
        } else {
            AntPathMatcher matcher = new AntPathMatcher();
            Iterator var4 = this.ignorePaths.iterator();

            String path;
            do {
                if (!var4.hasNext()) {
                    this.pathMatchCache.put(uri, false);
                    return false;
                }

                path = (String)var4.next();
            } while(!matcher.match(path, uri));

            this.pathMatchCache.put(uri, true);
            return true;
        }
    }

    private boolean isSwagger(HttpServletRequest request) {
        String referer = request.getHeader("referer");
        return referer != null && (referer.endsWith("/swagger-ui.html") || referer.endsWith("document.html"));
    }

    private boolean needWrapper(HttpServletRequest request) {
        if (request == null) {
            return true;
        } else {
            String uri = request.getRequestURI();
            return uri != null && !this.isValid(uri);
        }
    }

    public Object wrapperSuccess(HttpServletRequest request, HttpServletResponse response, Object data) throws IOException {
        if (this.needWrapper(request)) {
            return this.isSwagger(request) ? this.swaggerOutWrapper.wrapperSuccess(request, response, data) : this.outWrapper.wrapperSuccess(request, response, data);
        } else {
            return data;
        }
    }

    public Object wrapperError(HttpServletRequest request, HttpServletResponse response, Throwable e) throws IOException {
        if (this.needWrapper(request)) {
            return this.isSwagger(request) ? this.swaggerOutWrapper.wrapperError(request, response, e) : this.outWrapper.wrapperError(request, response, e);
        } else {
            return null;
        }
    }

    public void setOutWrapper(IOutWrapper outWrapper) {
        this.outWrapper = outWrapper;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
