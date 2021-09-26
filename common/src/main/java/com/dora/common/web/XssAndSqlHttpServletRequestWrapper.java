package com.dora.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

public class XssAndSqlHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private HttpServletRequest request;

    public XssAndSqlHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    public String getParameter(String name) {
        String value = this.request.getParameter(name);
        if (!StringUtils.isEmpty(value)) {
            value = StringEscapeUtils.escapeHtml4(value);
        }

        return value;
    }

    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if (parameterValues == null) {
            return null;
        } else {
            for(int i = 0; i < parameterValues.length; ++i) {
                String value = parameterValues[i];
                parameterValues[i] = StringEscapeUtils.escapeHtml4(value);
            }

            return parameterValues;
        }
    }
}
