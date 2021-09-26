package com.dora.common.web;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.dora.common.utils.RequestUtils;
import org.springframework.util.AntPathMatcher;

public class CommonFilter implements Filter {
    private List<String> noFilterUrls;
    private List<String> noFilterReferers;

    public CommonFilter() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        if (!this.isNoFilterUrl(request) && !this.isNoFilterReferer(request)) {
            XssAndSqlHttpServletRequestWrapper xssAndSqlHttpServletRequestWrapper = new XssAndSqlHttpServletRequestWrapper(request);
            filterChain.doFilter(xssAndSqlHttpServletRequestWrapper, servletResponse);
        } else {
            filterChain.doFilter(request, servletResponse);
        }

    }

    public void destroy() {
    }

    private boolean isNoFilterUrl(HttpServletRequest request) {
        if (this.noFilterUrls != null && this.noFilterUrls.size() != 0) {
            AntPathMatcher matcher = new AntPathMatcher();
            String uri = request.getRequestURI();
            Iterator var4 = this.noFilterUrls.iterator();

            String url;
            do {
                if (!var4.hasNext()) {
                    return false;
                }

                url = (String)var4.next();
            } while(!uri.equals(url) && !matcher.match(url, uri));

            request.setAttribute("_NO_FILTER_URL_", "true");
            request.setAttribute("_NO_FILTER_", "true");
            return true;
        } else {
            return false;
        }
    }

    private boolean isNoFilterReferer(HttpServletRequest request) {
        if (this.noFilterReferers != null && this.noFilterReferers.size() != 0) {
            AntPathMatcher matcher = new AntPathMatcher();
            String referer = RequestUtils.getReferer(request);
            if (referer != null && referer.length() != 0) {
                Iterator var4 = this.noFilterReferers.iterator();

                String url;
                do {
                    if (!var4.hasNext()) {
                        return false;
                    }

                    url = (String)var4.next();
                } while(!referer.equals(url) && !matcher.match(url, referer));

                request.setAttribute("_NO_FILTER_REFERER_", "true");
                request.setAttribute("_NO_FILTER_", "true");
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void setNoFilterUrls(List<String> noFilterUrls) {
        this.noFilterUrls = noFilterUrls;
    }

    public void setNoFilterReferers(List<String> noFilterReferers) {
        this.noFilterReferers = noFilterReferers;
    }
}
